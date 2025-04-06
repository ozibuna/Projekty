package bada_proj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DomSenioraDAOTest {
    private Dom_Seniora_DAO dao;
 @BeforeEach
    void setUp() throws Exception{
     DriverManagerDataSource datasource = new DriverManagerDataSource();
     datasource.setUrl("jdbc:oracle:thin:@194.29.170.4:1521:xe");
     datasource.setUsername("badagra14");
     datasource.setPassword("badagra14");
     datasource.setDriverClassName("oracle.jdbc.OracleDriver");

     /* Import JdbcTemplate */
     dao = new Dom_Seniora_DAO(new JdbcTemplate(datasource));
 }
    @Test
    void testList(){
        /* Import java.util */
        List<Dom_Seniora> listDomSeniora = dao.list();

        assertFalse(listDomSeniora.isEmpty());
    }
     @Test
     void testSave(){
         Dom_Seniora dom_Seniora = new Dom_Seniora(10,"Ku", "1.2.997", 6, 26, "Brak tak", 'F',(float) 7.0);
         dao.save(dom_Seniora);
     }
     @Test
     void testGet(){
         int id = 10;
         Dom_Seniora dom_Seniora = dao.get(id);
         
         assertNotNull(dom_Seniora);
     }
     
     @Test
     void testUpdate(){
         Dom_Seniora dom_Seniora = new Dom_Seniora();
         dom_Seniora.setNazwa("Zima");
         dom_Seniora.setData_Zalozenia("28.02.2024");

         dao.update(dom_Seniora);
     }
     
    @Test
    void testDelete(){
    	int id = 10;
    	dao.delete(id);
    }
}
