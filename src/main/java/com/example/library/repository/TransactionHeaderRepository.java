package com.example.library.repository;

import com.example.library.entity.TransactionHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionHeaderRepository extends JpaRepository<TransactionHeader, Long> {
    List<TransactionHeader> findAllByDeletedAtIsNull();
    List<TransactionHeader> findAllByUserIdAndDeletedAtIsNull(Long userId);
    List<TransactionHeader> findAllByUserIdAndReturnDateIsNullAndDeletedAtIsNull(Long userId);
    Optional<TransactionHeader> findByIdAndDeletedAtIsNull(Long id);
}
