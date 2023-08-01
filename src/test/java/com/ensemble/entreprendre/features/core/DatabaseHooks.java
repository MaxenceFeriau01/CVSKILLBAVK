package com.ensemble.entreprendre.features.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.ensemble.entreprendre.domain.Activity;
import com.ensemble.entreprendre.domain.City;
import com.ensemble.entreprendre.domain.InternStatus;
import com.ensemble.entreprendre.domain.Job;
import com.ensemble.entreprendre.domain.Role;
import com.ensemble.entreprendre.domain.enumeration.RoleEnum;
import com.ensemble.entreprendre.repository.IActivityRepository;
import com.ensemble.entreprendre.repository.ICityRepository;
import com.ensemble.entreprendre.repository.IInternStatusRepository;
import com.ensemble.entreprendre.repository.IJobRepository;
import com.ensemble.entreprendre.repository.IRoleRepository;

import io.cucumber.java.Before;

public class DatabaseHooks {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IActivityRepository activityRepository;

    @Autowired
    private ICityRepository cityRepository;

    @Autowired
    private IInternStatusRepository internStatusRepository;

    @Autowired
    private IJobRepository jobRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Before(order = 1)
    public void cleanupAndPopulateDatabase() throws SQLException {
        cleanDatabase();
        populateDataInDb();
    }

    private void cleanDatabase() throws SQLException {
        Connection c = dataSource.getConnection();
        Statement s = c.createStatement();

        // Disable FK
        s.execute("SET CONSTRAINTS ALL DEFERRED");

        // Find all tables and truncate them
        Set<String> tables = new HashSet<>();
        ResultSet rs = s.executeQuery(
                "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'");
        while (rs.next()) {
            tables.add(rs.getString(1));
        }
        rs.close();
        for (String table : tables) {
            s.executeUpdate("TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE");
        }

        // Idem for sequences
        Set<String> sequences = new HashSet<>();
        rs = s.executeQuery("SELECT sequence_name FROM information_schema.sequences WHERE sequence_schema = 'public'");
        while (rs.next()) {
            sequences.add(rs.getString(1));
        }
        rs.close();
        for (String seq : sequences) {
            s.executeUpdate("ALTER SEQUENCE " + seq + " RESTART WITH 1");
        }

        // Enable FK
        s.execute("SET CONSTRAINTS ALL IMMEDIATE");
        s.close();
        c.close();
    }

    private void populateDataInDb() {

        this.roleRepository.save(new Role(null, RoleEnum.ROLE_USER, null));
        this.roleRepository.save(new Role(null, RoleEnum.ROLE_COMPANY, null));
        this.roleRepository.save(new Role(null, RoleEnum.ROLE_ADMIN, null));

        this.activityRepository.save(new Activity(null, "Droit", null, null));
        this.activityRepository.save(new Activity(null, "Mécanique", null, null));

        this.jobRepository.save(new Job(null, "Technicien", null, null));
        this.jobRepository.save(new Job(null, "Juriste", null, null));

        this.cityRepository.save(new City(null, "BAMBECQUE", "59046", null));
        this.cityRepository.save(new City(null, "BERGUES ", "59067", null));

        this.internStatusRepository.save(new InternStatus(null, "Collégien", null, null));
        this.internStatusRepository.save(new InternStatus(null, "Lycéen", null, null));
        this.internStatusRepository.save(new InternStatus(null, "Etudiant", null, null));
    }

}
