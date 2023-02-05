package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.practicum.shareit.booking.dto.BookingAnswer;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    @MockBean
    private BookingService service;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    private final User user = new User(
            1L,
            "userName",
            "user@email.ru"
    );

    private final Item item = new Item(
            1L,
            "test",
            "test",
            true, user,
            1L
    );

    private final Booking booking = new Booking(
            1L,
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(2),
            item,
            user,
            BookingStatus.APPROVED
    );

    private final BookingAnswer bookingAnswer = new BookingAnswer(booking);

    private final BookingDto bookingDto = new BookingDto(booking);

    @Test
    @SneakyThrows
    void create() {
        when(service.add(Mockito.any(), Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(booking);

        MvcResult mvcResult = mvc.perform(post("/bookings")
                .header("x-sharer-user-id", user.getId())
                .content(mapper.writeValueAsString(bookingDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(mapper.writeValueAsString(bookingAnswer)));
        verify(service).add(Mockito.any(), Mockito.anyLong(), Mockito.anyLong());

    }

    @Test
    @SneakyThrows
    void approveBooking() {
        when(service.approveBooking(booking.getId(), true, user.getId()))
                .thenReturn(booking);

        MvcResult mvcResult = mvc.perform(patch("/bookings/{bookingId}", booking.getId())
                .header("x-sharer-user-id", user.getId())
                .param("approved", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(mapper.writeValueAsString(bookingAnswer)));
        verify(service).approveBooking(booking.getId(), true, user.getId());
    }

    @Test
    @SneakyThrows
    void getBooking() {
        when(service.getById(booking.getId(), user.getId()))
                .thenReturn(booking);

        MvcResult mvcResult = mvc.perform(get("/bookings/{bookingId}", booking.getId())
                .header("x-sharer-user-id", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(mapper.writeValueAsString(bookingAnswer)));
        verify(service).getById(booking.getId(), user.getId());
    }

    @Test
    @SneakyThrows
    void getBookingsByState_whenValid_thenReturnListOfBookings() {
        when(service.getBookingsByState(user.getId(), BookingState.ALL, null))
                .thenReturn(List.of(booking));

        MvcResult mvcResult = mvc.perform(get("/bookings")
                .header("x-sharer-user-id", user.getId())
                .param("state", "ALL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(),
                equalTo(mapper.writeValueAsString(List.of(bookingAnswer))));
        verify(service).getBookingsByState(user.getId(), BookingState.ALL, null);
    }

    @Test
    @SneakyThrows
    void getBookingsByState_whenInvalid_thenThrowUnknownStateException() {
        mvc.perform(get("/bookings")
                .header("x-sharer-user-id", user.getId())
                .param("state", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(service, never()).getBookingsByState(user.getId(), BookingState.ALL, null);
    }

    @Test
    @SneakyThrows
    void getBookingsByOwner() {
        when(service.getBookingsByOwner(user.getId(), BookingState.ALL, null))
                .thenReturn(List.of(booking));

        MvcResult mvcResult = mvc.perform(get("/bookings/owner")
                .header("x-sharer-user-id", user.getId())
                .param("state", "ALL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(),
                equalTo(mapper.writeValueAsString(List.of(bookingAnswer))));
        verify(service).getBookingsByOwner(user.getId(), BookingState.ALL, null);
    }
}