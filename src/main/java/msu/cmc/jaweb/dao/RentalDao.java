package msu.cmc.jaweb.dao;

import msu.cmc.jaweb.models.Rental;

import java.sql.Timestamp;
import java.util.List;

public interface RentalDao extends CommonDao<Rental, Long> {

    List<Rental> getAllRentalByPeriod(Timestamp from, Timestamp to);

}
