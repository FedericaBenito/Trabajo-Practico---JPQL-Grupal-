package managers;

import org.example.Articulo;
import org.example.Cliente;
import org.example.Factura;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacturaManager {
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public FacturaManager(boolean anularShowSQL) {
        Map<String, Object> properties = new HashMap<>();
        if(anularShowSQL){
            // Desactivar el show_sql (si está activado en el persistence.xml o configuración por defecto)
            properties.put("hibernate.show_sql", "false");
        }else{
            properties.put("hibernate.show_sql", "true");
        }
        emf = Persistence.createEntityManagerFactory("example-unit", properties);
        em = emf.createEntityManager();

    }

    public List<Factura> getFacturas(){
        String jpql = "FROM Factura";
        Query query = em.createQuery(jpql);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public List<Factura> getFacturasActivas(){
        //si quiero buscar distintos de NULL uso -> IS NOT NULL
        String jpql = "FROM Factura WHERE fechaBaja IS NULL ORDER BY fechaComprobante DESC";
        Query query = em.createQuery(jpql);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public List<Factura> getFacturasXNroComprobante(Long nroComprobante){
        String jpql = "FROM Factura WHERE nroComprobante = :nroComprobante";
        Query query = em.createQuery(jpql);
        query.setParameter("nroComprobante", nroComprobante);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public List<Factura> buscarFacturasXRangoFechas(LocalDate fechaInicio, LocalDate fechaFin){
        String jpql = "FROM Factura WHERE fechaComprobante >= :fechaInicio AND fechaComprobante <= :fechaFin";
        Query query = em.createQuery(jpql);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public Factura getFacturaXPtoVentaXNroComprobante(Integer puntoVenta, Long nroComprobante){
        String jpql = "FROM Factura WHERE puntoVenta = :puntoVenta AND nroComprobante = :nroComprobante";
        Query query = em.createQuery(jpql);
        query.setMaxResults(1);
        query.setParameter("puntoVenta", puntoVenta);
        query.setParameter("nroComprobante", nroComprobante);

        Factura factura = (Factura) query.getSingleResult();
        return factura;
    }

    public List<Factura> getFacturasXCliente(Long idCliente){
        String jpql = "FROM Factura WHERE cliente.id = :idCliente";
        Query query = em.createQuery(jpql);
        query.setParameter("idCliente", idCliente);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public List<Factura> getFacturasXCuitCliente(String cuitCliente){
        String jpql = "FROM Factura WHERE cliente.cuit = :cuitCliente";
        Query query = em.createQuery(jpql);
        query.setParameter("cuitCliente", cuitCliente);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public List<Factura> getFacturasXArticulo(Long idArticulo){ //INNER JOIN, LEFT JOIN, LEFT OUTER JOIN, etc
        StringBuilder jpql = new StringBuilder("SELECT fact FROM Factura AS fact LEFT OUTER JOIN fact.detallesFactura AS detalle");
        jpql.append(" WHERE detalle.id = :idArticulo");
        Query query = em.createQuery(jpql.toString());
        query.setParameter("idArticulo", idArticulo);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public Long getMaxNroComprobanteFactura(){ //MAX, MIN, COUNT, AVG, SUM
        StringBuilder jpql = new StringBuilder("SELECT MAX(nroComprobante) FROM Factura WHERE fechaBaja IS NULL");
        Query query = em.createQuery(jpql.toString());

        Long maxNroFactura = (Long) query.getSingleResult();
        return maxNroFactura;
    }

    public void cerrarEntityManager(){
        em.close();
        emf.close();
    }

    //Ejercicio 2
    public List<Factura> getFacturasUltimoMes() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicio = fechaActual.minusMonths(1); // Resta un mes a la fecha actual

        String jpql = "FROM Factura WHERE fechaComprobante BETWEEN :fechaInicio AND :fechaFin";
        Query query = em.createQuery(jpql);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaActual);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    //Ejercicio 3
    public Cliente getClienteConMasFacturas() {
        String jpql = "SELECT f.cliente, COUNT(f) AS cantidadFacturas " +
                "FROM Factura f " +
                "GROUP BY f.cliente " +
                "ORDER BY cantidadFacturas DESC";
        Query query = em.createQuery(jpql);
        query.setMaxResults(1); // Solo queremos el cliente con más facturas

        Object[] resultado = (Object[]) query.getSingleResult();
        Cliente cliente = (Cliente) resultado[0];

        return cliente;
    }

    //Ejercicio 4
    public List<Object[]> getArticulosMasVendidos() {
        String jpql = "SELECT fd.articulo, SUM(fd.cantidad) AS cantidadTotal " +
                "FROM FacturaDetalle fd " +
                "JOIN fd.articulo a " +
                "GROUP BY fd.articulo " +
                "ORDER BY cantidadTotal DESC";
        Query query = em.createQuery(jpql);
        List<Object[]> resultados = query.getResultList();
        return resultados;
    }

    //Ejercicio 5
    public List<Factura> getFacturasUltimosTresMesesPorCliente(Long clienteId) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicio = fechaActual.minusMonths(3); // Resta 3 meses a la fecha actual

        String jpql = "FROM Factura f " +
                "WHERE f.cliente.id = :clienteId " +
                "AND f.fechaComprobante BETWEEN :fechaInicio AND :fechaFin " +
                "ORDER BY f.fechaComprobante DESC";
        Query query = em.createQuery(jpql);
        query.setParameter("clienteId", clienteId);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaActual);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    //Ejercicio 6
    public Double getMontoTotalFacturadoPorCliente(Long clienteId) {
        String jpql = "SELECT SUM(f.total) FROM Factura f WHERE f.cliente.id = :clienteId";
        Query query = em.createQuery(jpql);
        query.setParameter("clienteId", clienteId);

        Double montoTotal = (Double) query.getSingleResult();
        return montoTotal != null ? montoTotal : 0.0;
    }

    //Ejercicio 7
    public List<Object[]> getArticulosYCantidadVendidosEnFactura(Long facturaId) {
        String jpql = "SELECT fd.articulo, fd.cantidad FROM FacturaDetalle fd WHERE fd.factura.id = :facturaId";
        Query query = em.createQuery(jpql);
        query.setParameter("facturaId", facturaId);

        List<Object[]> resultados = query.getResultList();
        return resultados;
    }

    //Ejercicio 8
    public Articulo getArticuloMasCaroEnFactura(Long facturaId) {
        String jpql = "SELECT fd.articulo FROM FacturaDetalle fd " +
                "WHERE fd.factura.id = :facturaId " +
                "ORDER BY fd.articulo.precioVenta DESC";
        Query query = em.createQuery(jpql);
        query.setParameter("facturaId", facturaId);
        query.setMaxResults(1); // Solo queremos el artículo más caro

        Articulo articulo = (Articulo) query.getSingleResult();
        return articulo;
    }

    //Ejercicio 9
    public Long contarTotalFacturas() {
        String jpql = "SELECT COUNT(f) FROM Factura f";
        Query query = em.createQuery(jpql);

        Long totalFacturas = (Long) query.getSingleResult();
        return totalFacturas;
    }

    //Ejercicio 10
    public List<Factura> getFacturasConTotalMayorA(Double valor) {
        String jpql = "FROM Factura f WHERE f.total > :valor";
        Query query = em.createQuery(jpql);
        query.setParameter("valor", valor);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    //Ejercicio 11
    public List<Factura> getFacturasPorNombreArticulo(String nombreArticulo) {
        String jpql = "SELECT DISTINCT fd.factura FROM FacturaDetalle fd JOIN fd.articulo a WHERE a.denominacion LIKE :nombreArticulo";
        Query query = em.createQuery(jpql);
        query.setParameter("nombreArticulo", "%" + nombreArticulo + "%");

        List<Factura> facturas = query.getResultList();
        return facturas;
    }











}
