package com.redfox.lunchmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redfox.lunchmanager.util.DateTimeUtil;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id AND v.restaurantId=:restaurantId"),
        @NamedQuery(name = Vote.DELETE_ALL_BY_RESTAURANT_ID, query = "DELETE FROM Vote v WHERE v.restaurantId=:restaurantId"),
        @NamedQuery(name = Vote.BY_DATE_AND_USER_ID, query = "SELECT v FROM Vote v WHERE v.voteDate=:voteDate AND v.userId=:userId"),
        @NamedQuery(name = Vote.ALL_SORTED_BY_USER_ID, query = "SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.voteDate DESC")
})
@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "votes_idx")
})
public class Vote extends AbstractBaseEntity {

    public static final String DELETE = "Vote.delete";
    public static final String DELETE_ALL_BY_RESTAURANT_ID = "Vote.deleteAllByRestaurantId";
    public static final String BY_DATE_AND_USER_ID = "Vote.getByDateAndUserId";
    public static final String ALL_SORTED_BY_USER_ID = "Vote.getAllByUserId";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Column(name = "user_id")
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    //  https://stackoverflow.com/a/44539145/548473
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "restaurant_id")
    private int restaurantId;

    @Column(name = "vote_date", nullable = false, columnDefinition = "date default now()")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    @NotNull
    private LocalDate voteDate;

    public Vote() {
    }

    public Vote(User user, Restaurant restaurant, LocalDate voteDate) {
        this(null, user, restaurant, voteDate);
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate voteDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.voteDate = voteDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", voteDate=" + voteDate +
                '}';
    }
}
