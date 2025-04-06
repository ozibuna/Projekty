package bada_proj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdresDAOTest {
        private Adres_DAO dao;
        @BeforeEach
        void setUp() throws Exception{
            DriverManagerDataSource datasource = new DriverManagerDataSource();
            datasource.setUrl("jdbc:oracle:thin:@194.29.170.4:1521:xe");
            datasource.setUsername("badagra14");
            datasource.setPassword("badagra14");
            datasource.setDriverClassName("oracle.jdbc.OracleDriver");

            /* Import JdbcTemplate */
            dao = new Adres_DAO(new JdbcTemplate(datasource));
        }
        @Test
        void testList(){
            /* Import java.util */
            List<Adres> listAdres = dao.list();

            assertFalse(listAdres.isEmpty());
        }
        @Test
        void testSave(){
            Adres adres = new Adres(11,"Warszawa", "Marszałkowska", "10C", "26A");
            dao.save(adres);
        }
        @Test
        void testGet(){
            int id = 11;
            Adres adres = dao.get(id);

            assertNotNull(adres);
        }

        @Test
        void testUpdate(){
            Adres adres = new Adres();
            adres.setMiasto("Pw");
            adres.setNr_Budynku("28K");

            dao.update(adres);
        }

        @Test
        void testDelete(){
            int id = 10;
            dao.delete(id);
        }
    }
