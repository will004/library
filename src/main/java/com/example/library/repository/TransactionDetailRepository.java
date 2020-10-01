package com.example.library.repository;

import com.example.library.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {
    List<TransactionDetail> findAllByDeletedAtIsNull();
    List<TransactionDetail> findAllByHeaderIdInAndDeletedAtIsNull(List<Long> headerIds);
    Optional<TransactionDetail> findByIdAndDeletedAtIsNull(Long id);
}
