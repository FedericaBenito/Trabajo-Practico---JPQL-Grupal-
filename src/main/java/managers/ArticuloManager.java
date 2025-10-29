//Ejercicio 12

package managers;

import org.example.Articulo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticuloManager {
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public ArticuloManager(boolean anularShowSQL) {
        Map<String, Object> properties = new HashMap<>();
        if (anularShowSQL) {
            properties.put("hibernate.show_sql", "false");
        } else {
            properties.put("hibernate.show_sql", "true");
        }
        emf = Persistence.createEntityManagerFactory("example-unit", properties);
        em = emf.createEntityManager();
    }

    public List<Articulo> getArticulosPorCodigoParcial(String codigoParcial) {
        String jpql = "FROM Articulo a WHERE a.codigo LIKE :codigoParcial";
        Query query = em.createQuery(jpql);
        query.setParameter("codigoParcial", "%" + codigoParcial + "%");

        List<Articulo> articulos = query.getResultList();
        return articulos;
    }

    public void cerrarEntityManager() {
        em.close();
        emf.close();
    }

    //Ejercicio 13
    public List<Articulo> getArticulosConPrecioMayorAlPromedio() {
        String jpql = "FROM Articulo a WHERE a.precioVenta > (SELECT AVG(a2.precioVenta) FROM Articulo a2)";
        Query query = em.createQuery(jpql);

        List<Articulo> articulos = query.getResultList();
        return articulos;
    }

}
