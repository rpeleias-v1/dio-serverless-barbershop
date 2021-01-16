package one.digitalinnovationone.barbershop.adapter.web.in;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigopeleias.barbershop.exception.BusinessException;
import com.rodrigopeleias.barbershop.ports.in.BookReservationCommand;
import com.rodrigopeleias.barbershop.ports.in.BookReservationUseCase;
import com.rodrigopeleias.barbershop.ports.in.CustomerCommand;
import com.rodrigopeleias.barbershop.ports.out.BookedReservation;
import jakarta.validation.ConstraintViolationException;
import one.digitalinnovationone.barbershop.adapter.web.in.dto.BookReservationDTO;
import one.digitalinnovationone.barbershop.adapter.web.out.ErrorDTO;
import one.digitalinnovationone.barbershop.repository.adapters.BarbershopRepositoryPortImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BookReservationFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private BookReservationUseCase bookReservationUseCase;
    private ObjectMapper objectMapper;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        objectMapper = new ObjectMapper();
        bookReservationUseCase = BookReservationUseCase.getInstance(new BarbershopRepositoryPortImpl());

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
        BookReservationDTO inputDto = objectMapper.readValue(input.getBody(), BookReservationDTO.class);
        BookReservationCommand command = getBookReservationCommand(inputDto);

        BookedReservation bookedReservation = bookReservationUseCase.book(command);
        String successBookConfirmation = objectMapper.writeValueAsString(bookedReservation);

        return response
                .withStatusCode(200)
                .withBody(successBookConfirmation);
    }

    private BookReservationCommand getBookReservationCommand(BookReservationDTO inputDto) {
        BookReservationCommand command = new BookReservationCommand(
                inputDto.getBarbershopName(),
                inputDto.getDate(),
                inputDto.getDayOfWeek(),
                inputDto.getTime(),
                getCustomerCommand(inputDto)
        );
        return command;
    }

    private CustomerCommand getCustomerCommand(BookReservationDTO inputDto) {
        CustomerCommand customerCommand = new CustomerCommand(
                inputDto.getCustomerName(),
                inputDto.getEmail(),
                inputDto.getPhone()
        );
        return customerCommand;
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

    private Map<String, String> createResponseHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return headers;
    }
}
