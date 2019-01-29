package ir.rezerwator.TheRoomReservator.service;

import ir.rezerwator.TheRoomReservator.dao.ReservationDaoInterface;

import ir.rezerwator.TheRoomReservator.dto.Reservation;
import ir.rezerwator.TheRoomReservator.exception.exceptions.CollidingReservationException;
import ir.rezerwator.TheRoomReservator.exception.exceptions.InputDataException;
import ir.rezerwator.TheRoomReservator.exception.exceptions.NotFoundException;
import ir.rezerwator.TheRoomReservator.model.ReservationEntity;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReservationServiceTest {

    private final static Date START_DATE = Date.from(LocalDateTime.of(2019, Month.JANUARY, 10, 11, 00, 00)
                                                                        .atZone(ZoneId.systemDefault()).toInstant());
    private final static Date END_DATE = Date.from(LocalDateTime.of(2019, Month.JANUARY, 10, 12, 00, 00)
            .atZone(ZoneId.systemDefault()).toInstant());
    private final static Date END_DATE_EARLIER_THAN_START_DATE = Date.from(LocalDateTime.of(2019, Month.JANUARY, 10, 10, 00, 00)
            .atZone(ZoneId.systemDefault()).toInstant());
    private final static Date END_DATE_4_MINUTES_59_SECONDS_AFTER_START_DATE = Date.from(LocalDateTime.of(2019, Month.JANUARY, 10, 11, 04, 59)
            .atZone(ZoneId.systemDefault()).toInstant());
    private final static Date END_DATE_2_HOURS_AND_1_SECOND_AFTER_START_DATE = Date.from(LocalDateTime.of(2019, Month.JANUARY, 10, 13, 00, 01)
            .atZone(ZoneId.systemDefault()).toInstant());

    @Mock
    private ReservationConverter reservationConverter;

    @Mock
    private RoomService roomService;

    @Mock
    private ReservationDaoInterface reservationDaoInterface;

    @InjectMocks
    private ReservationService reservationService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Reservation reservation;
    @Mock
    private ReservationEntity reservationEntity;
    @Mock
    private ReservationEntity collidingReservation;

    @Test
    public void ifOrganizationOrRoomDoesNotExistShouldThrowNotFoundException(){

    }

    @Test
    public void createShouldThrowExceptionWhenRoomOrOrganizationDoesNotExist(){
        NotFoundException notFoundException = new NotFoundException("room or organization does not exist");
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage(notFoundException.getMessage());

        when(roomService.readById(1,2)).thenThrow(notFoundException);

        reservationService.create(reservation, 1, 2);
        verify(roomService.readById(1, 2));
    }

    @Test
    public void createShouldThrowInputDataExceptionWhenThereIsACollidingReservation() {
        expectedException.expect(CollidingReservationException.class);
        expectedException.expectMessage("The room with chosen date is already booked. Choose different date");

        when(reservationDaoInterface.collidingReservations(START_DATE, END_DATE)).thenReturn(Arrays.asList(collidingReservation));
        when(reservation.getStartDate()).thenReturn(START_DATE);
        when(reservation.getEndDate()).thenReturn(END_DATE);

        reservationService.create(reservation, 1, 2);
        verify(reservationDaoInterface).collidingReservations(START_DATE, END_DATE);
    }

    @Test
    public void createShouldThrowInputDataExceptionWhenStartDateIsAfterEndDate(){
        expectedException.expect(InputDataException.class);
        expectedException.expectMessage("Incorrect dates format. Start date must be earlier than end date.");

        when(reservation.getStartDate()).thenReturn(START_DATE);
        when(reservation.getEndDate()).thenReturn(END_DATE_EARLIER_THAN_START_DATE);

        reservationService.create(reservation, 1, 2);
    }

    @Test
    public void createShouldThrowInputDataExceptionWhenReservationIntervalIsToSmall() {
        expectedException.expect(InputDataException.class);
        expectedException.expectMessage("Reservation duration must be equal or longer than 5 minutes and equal or shorter than 2 hours.");

        when(reservation.getStartDate()).thenReturn(START_DATE);
        when(reservation.getEndDate()).thenReturn(END_DATE_4_MINUTES_59_SECONDS_AFTER_START_DATE);

        reservationService.create(reservation, 1, 2);
    }

    @Test
    public void createShouldThrowInputDataExceptionWhenReservationIntervalIsToBig() {
        expectedException.expect(InputDataException.class);
        expectedException.expectMessage("Reservation duration must be equal or longer than 5 minutes and equal or shorter than 2 hours.");

        when(reservation.getStartDate()).thenReturn(START_DATE);
        when(reservation.getEndDate()).thenReturn(END_DATE_2_HOURS_AND_1_SECOND_AFTER_START_DATE);

        reservationService.create(reservation, 1, 2);
    }

    @Test
    public void createDateShouldReturnCreatedReservationWhenInputDataIsCorrect() {
        when(reservation.getStartDate()).thenReturn(START_DATE);
        when(reservation.getEndDate()).thenReturn(END_DATE);
        when(reservationConverter.convertToEntity(reservation)).thenReturn(reservationEntity);
        when(reservationConverter.convertToDto(reservationEntity)).thenReturn(reservation);
        when(reservationDaoInterface.create(reservationEntity)).thenReturn(reservationEntity);

        Reservation result = reservationService.create(reservation,  1, 2);

        verify(reservationDaoInterface).create(reservationEntity);
        assertThat(result.getStartDate()).isEqualTo(START_DATE);
        assertThat(result.getEndDate()).isEqualTo(END_DATE);
    }

    @Test
    public void readByIdShouldThrowNotFoundExceptionWhenReservationDoesNotExist(){
        NotFoundException notFoundException = new NotFoundException("The reservation with this id doesn't exist");
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage(notFoundException.getMessage());

        when(reservationDaoInterface.readById(3)).thenThrow(notFoundException);

        reservationService.readById(3, 1,2);
        verify(reservationDaoInterface).readById(3);
    }

}