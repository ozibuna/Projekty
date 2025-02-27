package bada_proj;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class Dom_Seniora_DAO {

    /* Import org.springframework.jd....Template */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Dom_Seniora_DAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }
    /* Import java.util.List */ /*(zawiera info z bazy danych)*/
    public List<Dom_Seniora> list(){
        String sql = "SELECT * FROM DOMY_SENIORA";

        List<Dom_Seniora> listDomSeniora = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Dom_Seniora.class));
        return listDomSeniora;
    }
    
    /* Insert – wstawianie nowego wiersza do bazy */
    public void save(Dom_Seniora dom_Seniora) {
    	SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
    	insertActor.withTableName("DOMY_SENIORA").usingColumns("id_Domu_Seniora","nazwa", "data_Zalozenia", "liczba_Pokoi", "max_Liczba_Mieszkancow", "opis_Placowki", "czynny_Calodobowo", "srednia_Cena_Mieszkania");
    	
    	BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(dom_Seniora);
    	insertActor.execute(param);
    }
    
    /* Read – odczytywanie danych z bazy */
    @SuppressWarnings("deprecation")
	public Dom_Seniora get(int id) {
    	String sql = "SELECT * FROM SALES WHERE ID_DOMU_SENIORA = ?";
    	Object[] args = {id};
		Dom_Seniora dom_Seniora = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Dom_Seniora.class));
        return dom_Seniora;
    }
    public Dom_Seniora get1(int id) {
    	Object[] args = {id};
    	String sql = "SELECT * FROM SALES WHERE ID_DOMU_SENIORA = " + args[0];
    	Dom_Seniora dom_Seniora = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Dom_Seniora.class));
        return dom_Seniora;
    }
    /* Update – aktualizacja danych */
    public void update(Dom_Seniora dom_Seniora) {
        String sql = "UPDATE DOMY_SENIORA SET Nazwa=: nazwa, Data_Zalozenia=:data_Zalozenia, Liczba_Pokoi=:liczba_Pokoi,Max_Liczba_Mieszkancow=:max_Liczba_Mieszkancow, Opis_Placowki=:opis_Placowki, Czynny_Calodobowo=:czynny_Calodobowo, Srednia_Cena_Mieszkania=:srednia_Cena_Mieszkania WHERE Id_Domu_Seniora=:id_Domu_Seniora";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(dom_Seniora);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        
        template.update(sql, param);
    }
    
    /* Delete – wybrany rekord z danym id */
    public void delete(int id) {
    	String sql = "DELETE FROM DOMY_SENIORA WHERE ID_DOMU_SENIORA = ?";
    	jdbcTemplate.update(sql, id);
    }
}
