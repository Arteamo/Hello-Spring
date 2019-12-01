package com.arteamo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @NonNull
    private String subject;

    @NonNull
    private String text;

    @NonNull
    private String author;

    @NonNull
    @Column(columnDefinition = "BOOLEAN")
    private boolean isCompleted;

    public Message(@NonNull String subject, @NonNull String text, @NonNull String author) {
        this.subject = subject;
        this.text = text;
        this.author = author;
        this.isCompleted = false;
    }
}
