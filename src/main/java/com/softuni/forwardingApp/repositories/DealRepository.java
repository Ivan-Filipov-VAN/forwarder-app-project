package com.softuni.forwardingApp.repositories;

import com.softuni.forwardingApp.models.entity.DealEntity;
import com.softuni.forwardingApp.models.enums.ShipmentStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<DealEntity, Long> {

//    @Query("SELECT new com.softuni.forwardingApp.models.view.DealViewModel(d.id, d.type, d.date, d.mawb, d.hawb, c.name, a.name," +
//            " e.email, d.pieces, d.actualWeight, d.chargeableWeight, d.country, d.airport)" +
//            " FROM DealEntity d" +
//            " JOIN d.company c" +
//            " JOIN d.agent a" +
//            " JOIN d.employee e" +
//            " ORDER BY d.id DESC")
//    List<DealViewModel> findAllDealsViewModel();

    List<DealEntity> findAll();
//    @Query("SELECT new com.softuni.forwardingApp.models.view.DealViewModel(d.id, d.type, d.date, d.mawb, d.hawb, c.name, a.name," +
//            " e.email, d.pieces, d.actualWeight, d.chargeableWeight, d.country, d.airport)" +
//            " FROM DealEntity d" +
//            " JOIN d.company c" +
//            " JOIN d.agent a" +
//            " JOIN d.employee e" +
//            " WHERE c.id = ?1" +
//            " ORDER BY d.id DESC")
//    List<DealViewModel> findAllDealsViewModelByCompanyID(Long id);
    @Query("SELECT d FROM DealEntity d WHERE  d.status !=?1")
    Page<DealEntity> findAllDealsInTransit(Pageable pageable, ShipmentStatusEnum status);

    @Query("SELECT d FROM DealEntity d JOIN d.company c WHERE c.id = ?1")
    Page<DealEntity> findByEmployeeId(Pageable pageable, Long id);

    @Query("SELECT d FROM DealEntity d JOIN d.company c WHERE c.id = ?1 AND d.status !=?2")
    Page<DealEntity> findByEmployeeIdInTransit(Pageable pageable, Long id, ShipmentStatusEnum status);
}
