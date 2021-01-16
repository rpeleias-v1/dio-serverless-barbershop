package one.digitalinnovationone.barbershop.adapter.web.in;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigopeleias.barbershop.exception.BusinessException;
import com.rodrigopeleias.barbershop.ports.in.AvailabilityCommand;
import com.rodrigopeleias.barbershop.ports.in.CreateBarbershopCommand;
import com.rodrigopeleias.barbershop.ports.in.CreateBarbershopUseCase;
import com.rodrigopeleias.barbershop.ports.out.CreatedBarbershop;
import jakarta.validation.ConstraintViolationException;
import one.digitalinnovationone.barbershop.adapter.web.in.dto.CreateBarbershopDTO;
import one.digitalinnovationone.barbershop.adapter.web.out.ErrorDTO;
import one.digitalinnovationone.barbershop.repository.adapters.BarbershopRepositoryPortImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateBarbershopFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private CreateBarbershopUseCase createBarbershopUseCase;
    private ObjectMapper objectMapper;

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        objectMapper = new ObjectMapper();
        createBarbershopUseCase = CreateBarbershopUseCase.getInstance(new BarbershopRepositoryPortImpl());

        Map<String, String> headers = createResponseHeaders();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        try {
            return doHandleRequest(input, response);
        } catch (IOException e) {
            return doHandleError(response, e, 500);
        } catch (ConstraintViolationException | IllegalArgumentException e) {
            return doHandleError(response, e, 400);
        } catch (BusinessException e) {
            return doHandleError(response, e, e.getErrorCode());
        } catch (Exception e) {
            return doHandleError(response, e, 502);
        }
    }

    private APIGatewayProxyResponseEvent doHandleRequest(APIGatewayProxyRequestEvent
                                                                 input, APIGatewayProxyResponseEvent response) throws com.fasterxml.jackson.core.JsonProcessingException {
        CreateBarbershopDTO inputDto = objectMapper.readValue(input.getBody(), CreateBarbershopDTO.class);

        CreateBarbershopCommand barbershopCommand = getBarbershopCommand(inputDto);
        CreatedBarbershop createdBarbershop = createBarbershopUseCase.create(barbershopCommand);
        String successResponseCreation = objectMapper.writeValueAsString(createdBarbershop);

        return response
                .withStatusCode(200)
                .withBody(successResponseCreation);
    }

    private APIGatewayProxyResponseEvent doHandleError(APIGatewayProxyResponseEvent response, Exception e, int errorCode) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), errorCode);
        e.printStackTrace();

        String errorBody;
        try {
            errorBody = objectMapper.writeValueAsString(errorDTO);
            return response
                    .withBody(errorBody)
                    .withStatusCode(errorCode);
        } catch (JsonProcessingException jsonProcessingException) {
            errorBody = jsonProcessingException.getMessage();
        }

        return response
                .withBody(errorBody)
                .withStatusCode(errorCode);
    }


    private CreateBarbershopCommand getBarbershopCommand(CreateBarbershopDTO inputDto) {
        return new CreateBarbershopCommand(inputDto.getName(),
                inputDto.getAddress(),
                inputDto.getCity(),
                getAvailabilityCommand(inputDto));
    }

    private AvailabilityCommand getAvailabilityCommand(CreateBarbershopDTO inputDto) {
        return new AvailabilityCommand(
                inputDto.getDaysOfWeek(),
                inputDto.getOpenTime(),
                inputDto.getCloseTime(),
                inputDto.getDuration());
    }

    private Map<String, String> createResponseHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return headers;
    }
}
