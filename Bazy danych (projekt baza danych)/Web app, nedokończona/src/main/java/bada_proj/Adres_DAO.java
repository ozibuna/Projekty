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
public class Adres_DAO { /* Import org.springframework.jd....Template */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Adres_DAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }
    /* Import java.util.List */ /*(zawiera info z bazy danych)*/
    public List<Adres> list(){
        String sql = "SELECT * FROM ADRESY";

        List<Adres> listAdres = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Adres.class));
        return listAdres;
    }

    /* Insert – wstawianie nowego wiersza do bazy */
    public void save(Adres adres) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("ADRESY").usingColumns("id_Adresu", "miasto", "ulica", "nr_Lokalu", "nr_Budynku");

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(adres);
        insertActor.execute(param);
    }

    /* Read – odczytywanie danych z bazy */
    @SuppressWarnings("deprecation")
    public Adres get(int id) {
        String sql = "SELECT * FROM ADRESY WHERE ID_ADRESU = ?";
        Object[] args = {id};
        Adres adres = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Adres.class));
        return adres;
    }
    public Adres get1(int id) {
        Object[] args = {id};
        String sql = "SELECT * FROM ADRESY WHERE ID_ADRESU = " + args[0];
        Adres adres = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Adres.class));
        return adres;
    }
    /* Update – aktualizacja danych */
    public void update(Adres adres) {
        String sql = "UPDATE ADRESY SET Miasto=:miasto, Ulica=:ulica,Nr_Lokalu=:nr_Lokalu, Nr_Budynku=:nr_Budynku WHERE Id_Adresu=:id_Adresu";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(adres);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);

        template.update(sql, param);
    }

    /* Delete – wybrany rekord z danym id */
    public void delete(int id) {
        String sql = "DELETE FROM ADRESY WHERE ID_ADRESU = ?";
        jdbcTemplate.update(sql, id);
    }
}
