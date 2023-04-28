package com.example.airbnb.repository;

import com.example.airbnb.model.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostStatusRepository extends JpaRepository<PostStatus, Long> {
}
