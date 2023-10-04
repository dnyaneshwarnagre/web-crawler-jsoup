package com.rss.feed.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RSSFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;
    private String title;
    private String description;
    private String author;
    private String publicationDate;
    private String thumbnail;

    @ElementCollection
    private List<String> categories;
}