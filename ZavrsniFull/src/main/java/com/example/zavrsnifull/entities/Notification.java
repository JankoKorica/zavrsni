package com.example.zavrsnifull.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "content")
    private String content;

    @Column(name = "viewed")
    private Boolean viewed;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;

}
