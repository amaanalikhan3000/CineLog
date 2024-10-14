    package com.cineLog.cineLog.entity;

    import lombok.Data;

    import lombok.NoArgsConstructor;
    import lombok.NonNull;
    import org.bson.types.ObjectId;
    import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.index.Indexed;
    import org.springframework.data.mongodb.core.mapping.DBRef;
    import org.springframework.data.mongodb.core.mapping.Document;

    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

    @Data
    @Document(collection = "userEntity")
    @NoArgsConstructor
    public class UserEntity {
        @Id
        private ObjectId userId;


        @Indexed(unique = true)
        @NonNull
        private String username;
        @NonNull
        private String password;
        private String email;
        private String profilepic;
        private List<String> favorites = new ArrayList<>();
        private List<String> watchlist = new ArrayList<>();
        private Date createdAt;
        private Date updatedAt;

        @DBRef
        private List<ReviewEntity> reviewEntities = new ArrayList<>();

        // for what type of roles like admin , user etc
        private List<String> roles;


    }
