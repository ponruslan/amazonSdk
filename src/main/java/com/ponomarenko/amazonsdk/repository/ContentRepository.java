package com.ponomarenko.amazonsdk.repository;

import com.ponomarenko.amazonsdk.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content,String> {
}
