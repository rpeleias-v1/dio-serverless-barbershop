package one.digitalinnovationone.barbershop.adapter.web.in;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigopeleias.barbershop.ports.in.AvailabilityCommand;
import com.rodrigopeleias.barbershop.ports.in.BookReservationCommand;
import com.rodrigopeleias.barbershop.ports.in.BookReservationUseCase;
import com.rodrigopeleias.barbershop.ports.in.CreateBarbershopCommand;
import com.rodrigopeleias.barbershop.ports.in.CreateBarbershopUseCase;
import com.rodrigopeleias.barbershop.ports.in.CustomerCommand;
import com.rodrigopeleias.barbershop.ports.out.BookedReservation;
import com.rodrigopeleias.barbershop.ports.out.CreatedBarbershop;
import one.digitalinnovationone.barbershop.adapter.web.in.dto.BookReservationDTO;
import one.digitalinnovationone.barbershop.adapter.web.in.dto.CreateBarbershopDTO;
import one.digitalinnovationone.barbershop.repository.adapters.BarbershopRepositoryPortImpl;

import java.util.HashMap;
import java.util.Map;

public class BookReservationFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private BookReservationUseCase bookReservationUseCase;
    private ObjectMapper objectMapper;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        objectMapper = new ObjectMapper();
        bookReservationUseCase = BookReservationUseCase.getInstance(new BarbershopRepositoryPortImpl());

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(createResponseHeaders());

        try {
            BookReservationDTO inputDTO = objectMapper.readValue(input.getBody(), BookReservationDTO.class);

            BookReservationCommand command = getBookReservationCommand(inputDTO);

            BookedReservation bookedReservation = bookReservationUseCase.book(command);

            String successResponseCreation = objectMapper.writeValueAsString(bookedReservation);

            return response
                    .withStatusCode(200)
                    .withBody(successResponseCreation);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private BookReservationCommand getBookReservationCommand(BookReservationDTO inputDTO) {
        return new BookReservationCommand(
                inputDTO.getBarbershopName(),
                inputDTO.getDate(),
                inputDTO.getDayOfWeek(),
                inputDTO.getTime(), getCustomerCommand(inputDTO));
    }

    private CustomerCommand getCustomerCommand(BookReservationDTO inputDTO) {
        return new CustomerCommand(inputDTO.getCustomerName(), inputDTO.getEmail(), inputDTO.getPhone());
    }

    private Map<String, String> createResponseHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return headers;
    }
}
