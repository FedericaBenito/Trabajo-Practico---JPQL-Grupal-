package managers;

import funciones.FuncionApp;
import org.example.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainConsultasJPQL {

    public static void main(String[] args) {
        //REPOSITORIO-> https://github.com/gerardomagni/jpqlquerys.git

        //buscarFacturas();
        //buscarFacturasActivas();
        //buscarFacturasXNroComprobante();
        //buscarFacturasXRangoFechas();
        //buscarFacturaXPtoVentaXNroComprobante();
        //buscarFacturasXCliente();
        //buscarFacturasXCuitCliente();
        //buscarFacturasXArticulo();
        //mostrarMaximoNroFactura();
        //buscarClientesXIds();
        //buscarClientesXRazonSocialParcial();
    }


    public static void buscarFacturas(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturas();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasActivas(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasActivas();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXNroComprobante(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXNroComprobante(796910l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXRangoFechas(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaInicio = FuncionApp.restarSeisMeses(fechaActual);
            List<Factura> facturas = mFactura.buscarFacturasXRangoFechas(fechaInicio, fechaActual);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturaXPtoVentaXNroComprobante(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Factura factura = mFactura.getFacturaXPtoVentaXNroComprobante(2024, 796910l);
            mostrarFactura(factura);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXCliente(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXCliente(7l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXCuitCliente(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXCuitCliente("27236068981");
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXArticulo(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXArticulo(6l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void mostrarMaximoNroFactura(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Long nroCompMax = mFactura.getMaxNroComprobanteFactura();
            System.out.println("N° " + nroCompMax);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarClientesXIds(){
        ClienteManager mCliente = new ClienteManager(true);
        try {
            List<Long> idsClientes = new ArrayList<>();
            idsClientes.add(1l);
            idsClientes.add(2l);
            List<Cliente> clientes = mCliente.getClientesXIds(idsClientes);
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }

    public static void buscarClientesXRazonSocialParcial(){
        ClienteManager mCliente = new ClienteManager(true);
        try {
            List<Long> idsClientes = new ArrayList<>();
            idsClientes.add(1l);
            idsClientes.add(2l);
            List<Cliente> clientes = mCliente.getClientesXRazonSocialParcialmente("Lui");
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }



    public static void mostrarFactura(Factura factura){
        List<Factura> facturas = new ArrayList<>();
        facturas.add(factura);
        mostrarFacturas(facturas);
    }

    public static void mostrarFacturas(List<Factura> facturas){
        for(Factura fact : facturas){
            System.out.println("N° Comp: " + fact.getStrProVentaNroComprobante());
            System.out.println("Fecha: " + FuncionApp.formatLocalDateToString(fact.getFechaComprobante()));
            System.out.println("CUIT Cliente: " + FuncionApp.formatCuitConGuiones(fact.getCliente().getCuit()));
            System.out.println("Cliente: " + fact.getCliente().getRazonSocial() + " ("+fact.getCliente().getId() + ")");
            System.out.println("------Articulos------");
            for(FacturaDetalle detalle : fact.getDetallesFactura()){
                System.out.println(detalle.getArticulo().getDenominacion() + ", " + detalle.getCantidad() + " unidades, $" + FuncionApp.getFormatMilDecimal(detalle.getSubTotal(), 2));
            }
            System.out.println("Total: $" + FuncionApp.getFormatMilDecimal(fact.getTotal(),2));
            System.out.println("*************************");
        }
    }

    public static void listarTodosLosClientes() {
        ClienteManager mCliente = new ClienteManager(true);
        try {
            List<Cliente> clientes = mCliente.getTodosLosClientes();
            for (Cliente cli : clientes) {
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razón Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mCliente.cerrarEntityManager();
        }
    }

    public static void listarFacturasUltimoMes() {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasUltimoMes();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void mostrarClienteConMasFacturas() {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Cliente cliente = mFactura.getClienteConMasFacturas();
            System.out.println("Cliente con más facturas:");
            System.out.println("Id: " + cliente.getId());
            System.out.println("CUIT: " + cliente.getCuit());
            System.out.println("Razón Social: " + cliente.getRazonSocial());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void listarArticulosMasVendidos() {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Object[]> resultados = mFactura.getArticulosMasVendidos();
            for (Object[] resultado : resultados) {
                Articulo articulo = (Articulo) resultado[0];
                Long cantidadTotal = (Long) resultado[1];
                System.out.println("Artículo: " + articulo.getDenominacion());
                System.out.println("Cantidad total vendida: " + cantidadTotal);
                System.out.println("-----------------");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void listarFacturasUltimosTresMesesPorCliente(Long clienteId) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasUltimosTresMesesPorCliente(clienteId);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void mostrarMontoTotalFacturadoPorCliente(Long clienteId) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Double montoTotal = mFactura.getMontoTotalFacturadoPorCliente(clienteId);
            System.out.println("Monto total facturado por el cliente (ID: " + clienteId + "): $" + montoTotal);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void listarArticulosVendidosEnFactura(Long facturaId) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Object[]> resultados = mFactura.getArticulosYCantidadVendidosEnFactura(facturaId);
            System.out.println("Artículos vendidos en la factura (ID: " + facturaId + "):");
            for (Object[] resultado : resultados) {
                Articulo articulo = (Articulo) resultado[0];
                Integer cantidad = (Integer) resultado[1];
                System.out.println("- " + articulo.getDenominacion() + ", Cantidad: " + cantidad);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void mostrarArticuloMasCaroEnFactura(Long facturaId) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Articulo articulo = mFactura.getArticuloMasCaroEnFactura(facturaId);
            System.out.println("Artículo más caro en la factura (ID: " + facturaId + "):");
            System.out.println("- " + articulo.getDenominacion() + ", Precio: $" + articulo.getPrecioVenta());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void mostrarTotalFacturas() {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Long totalFacturas = mFactura.contarTotalFacturas();
            System.out.println("Cantidad total de facturas generadas en el sistema: " + totalFacturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void listarFacturasConTotalMayorA(Double valor) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasConTotalMayorA(valor);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void listarFacturasPorNombreArticulo(String nombreArticulo) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasPorNombreArticulo(nombreArticulo);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void listarArticulosPorCodigoParcial(String codigoParcial) {
        ArticuloManager mArticulo = new ArticuloManager(true);
        try {
            List<Articulo> articulos = mArticulo.getArticulosPorCodigoParcial(codigoParcial);
            for (Articulo articulo : articulos) {
                System.out.println("ID: " + articulo.getId() + ", Código: " + articulo.getCodigo() + ", Denominación: " + articulo.getDenominacion());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mArticulo.cerrarEntityManager();
        }
    }

    public static void listarArticulosConPrecioMayorAlPromedio() {
        ArticuloManager mArticulo = new ArticuloManager(true);
        try {
            List<Articulo> articulos = mArticulo.getArticulosConPrecioMayorAlPromedio();
            for (Articulo articulo : articulos) {
                System.out.println("ID: " + articulo.getId() +
                        ", Denominación: " + articulo.getDenominacion() +
                        ", Precio: $" + articulo.getPrecioVenta());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mArticulo.cerrarEntityManager();
        }
    }

    public static void listarClientesConFacturas() {
        ClienteManager mCliente = new ClienteManager(true);
        try {
            List<Cliente> clientes = mCliente.getClientesConFacturas();
            for (Cliente cliente : clientes) {
                System.out.println("ID: " + cliente.getId() +
                        ", CUIT: " + cliente.getCuit() +
                        ", Razón Social: " + cliente.getRazonSocial());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mCliente.cerrarEntityManager();
        }
    }















}
