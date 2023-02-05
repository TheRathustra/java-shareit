package ru.practicum.shareit.server.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookings", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDateTime start;

    @Column(name = "end_date")
    private LocalDateTime end;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booker_id", referencedColumnName = "id")
    private User booker;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;


}
