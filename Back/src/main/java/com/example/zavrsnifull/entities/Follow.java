package com.example.zavrsnifull.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "follows")
@Data
public class Follow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user1id", referencedColumnName = "id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "user2id", referencedColumnName = "id")
    private User followee;

}
