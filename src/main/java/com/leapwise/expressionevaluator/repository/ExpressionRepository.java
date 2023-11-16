package com.leapwise.expressionevaluator.repository;
import com.leapwise.expressionevaluator.model.ExpressionIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpressionRepository extends JpaRepository<ExpressionIdentifier, String>{
}
