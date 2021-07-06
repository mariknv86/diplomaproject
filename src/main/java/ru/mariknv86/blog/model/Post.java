package ru.mariknv86.blog.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mariknv86.blog.model.enums.ModerationStatus;

@Data
@Entity
@Builder
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Column(name = "is_active", nullable = false)
    private byte isActive;

    @Column(name = "moderation_status", columnDefinition="ENUM('NEW','ACCEPTED', 'DECLINED')", nullable = false)
    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime time;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "tag2post",
        joinColumns = {@JoinColumn(name = "post_id")},
        inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tagList;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private List<PostVote> votes;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private List<PostComment> comments;



    public void incrementUserView() {
        setViewCount(getViewCount() + 1);
    }

}
