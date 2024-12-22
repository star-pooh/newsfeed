package org.team14.newsfeed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  // TODO : not null 제약 추가
  @Column(columnDefinition = "longtext")
  private String content;

  // TODO : not null 제약 추가
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public Post(String title, String content, User user) {
    this.title = title;
    this.content = content;
    this.user = user;
  }

  public Post() {

  }
}
