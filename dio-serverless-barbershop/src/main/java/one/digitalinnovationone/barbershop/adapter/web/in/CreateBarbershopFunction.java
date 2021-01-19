package one.digitalinnovationone.barbershop.adapter.web.in;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigopeleias.barbershop.ports.in.AvailabilityCommand;
import com.rodrigopeleias.barbershop.ports.in.CreateBarbershopCommand;
import com.rodrigopeleias.barbershop.ports.in.CreateBarbershopUseCase;
import com.rodrigopeleias.barbershop.ports.out.CreatedBarbershop;
import one.digitalinnovationone.barbershop.adapter.out.repository.MockBarbershopRepositoryPortImpl;
import one.digitalinnovationone.barbershop.adapter.web.in.dto.CreateBarbershopDTO;
import one.digitalinnovationone.barbershop.repository.adapters.BarbershopRepositoryPortImpl;

import java.util.HashMap;
import java.util.Map;

public class CreateBarbershopFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private CreateBarbershopUseCase createBarbershopUseCase;
    private ObjectMapper objectMapper;

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        objectMapper = new ObjectMapper();
        createBarbershopUseCase = CreateBarbershopUseCase.getInstance(new BarbershopRepositoryPortImpl());
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(createResponseHeaders());

        try {
            CreateBarbershopDTO inputDTO = objectMapper.readValue(input.getBody(), CreateBarbershopDTO.class);

            AvailabilityCommand availabilityCommand = getAvailabilityCommand(inputDTO);
            CreateBarbershopCommand command = getCommand(inputDTO, availabilityCommand);

            CreatedBarbershop createdBarbershop = createBarbershopUseCase.create(command);

            String successResponseCreation = objectMapper.writeValueAsString(createdBarbershop);

            return response
                    .withStatusCode(200)
                    .withBody(successResponseCreation);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private CreateBarbershopCommand getCommand(CreateBarbershopDTO inputDTO, AvailabilityCommand availabilityCommand) {
        return new CreateBarbershopCommand(
                inputDTO.getName(),
                inputDTO.getAddress(),
                inputDTO.getCity(), availabilityCommand);
    }

    private AvailabilityCommand getAvailabilityCommand(CreateBarbershopDTO inputDTO) {
        return new AvailabilityCommand(
                inputDTO.getDaysOfWeek(),
                inputDTO.getOpenTime(),
                inputDTO.getCloseTime(),
                inputDTO.getDuration());
    }

    private Map<String, String> createResponseHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return headers;
    }
}
