package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import bean.Adicional;
import bean.ComboPromocional;
import bean.Entrada;
import bean.Producto;
import bean.ProductoView;
import bean.Venta;
import bean.VentaView;
import bean.srv.ProductoSrv;
import bean.srv.VentaSRV;

public class SistemaCine 
{
	private List<Entrada> entradas;
	private List<Adicional> adicionales;
	private List<ComboPromocional> combos;
	private List<Venta> ventas;
	private Venta ventaActual;
	
	private static SistemaCine instancia = null;
	
	private SistemaCine()
	{
//		entradas = new ArrayList<Entrada>();
//		adicionales = new ArrayList<Adicional>();
//		combos = new ArrayList<ComboPromocional>();
		entradas = ProductoSrv.leerEntradas();
		adicionales = ProductoSrv.leerAdicional();
		combos = ProductoSrv.leerCombos();
		ventas = new ArrayList<Venta>();
	}
	
	public static SistemaCine getInstance()
	{
		if(instancia == null) 
		{
			instancia = new SistemaCine();
	    }
		return instancia;
	}
	
	public VentaView iniciarVenta()
	{
		ventaActual = new Venta();
		return ventaActual.getView();
	}
	
	public void agregarProducto(ProductoView prod, int cantidad)
	{
		Producto p = buscarProducto(prod.getCodigo());
		ventaActual.agregarItem(p, cantidad);
	}
	
	public VentaView actualizarVistaVenta()
	{
		return ventaActual.getView();
	}
	
	public float totalVenta()
	{
		return ventaActual.getTotal();
	}
	
	public float registrarVenta()
	{
		ventas.add(ventaActual);
		VentaSRV.grabarVenta(ventaActual);
		return ventaActual.getTotal();
		//aca va la bajada a base de datos
	}
	
	private Producto buscarProducto(int codigo)
	{
		for(Producto p : entradas)
			if(p.getCodigo() == codigo)
				return p;
		for(Producto p : adicionales)
			if(p.getCodigo() == codigo)
				return p;
		for(Producto p : combos)
			if(p.getCodigo() == codigo)
				return p;
		return null;
	}
	
	public Vector<ProductoView> getEntradas()
	{
		Vector<ProductoView> ent = new Vector<ProductoView>();
//		entradas = ProductoSrv.leerEntradas();
		for(Producto p : entradas)
			ent.add(p.getView());
		return ent;
	}
	
	public Vector<ProductoView> getAdicionales()
	{
		Vector<ProductoView> ad = new Vector<ProductoView>();
//		adicionales = ProductoSrv.leerAdicional();
		for(Producto p : adicionales)
			ad.add(p.getView());
		return ad;
	}
	
	public ProductoView getProducto(int codigo)
	{
		Producto p = buscarProducto(codigo);
		if(p != null)
			return p.getView();
		return null;
	}
	
	public Vector<ProductoView> getCombos()
	{
		Vector<ProductoView> co = new Vector<ProductoView>();
//		combos = ProductoSrv.leerCombos();
		for(Producto p : combos)
			co.add(p.getView());
		return co;
	}
	
	public Vector<Vector<String>> getVentasAnteriores()
	{
		Vector<Vector<String>> vent = new Vector<Vector<String>>();
		List<Venta> vs = VentaSRV.leerVentas();
		for(Venta v : vs)
		{
			Vector<String> strs = new Vector<String>();
			strs.add(String.valueOf(v.getNumero()));
			strs.add(v.getFecha());
			strs.add(String.valueOf(v.getTotal()));
			vent.add(strs);
		}
		return vent;
	}
	
	public ProductoView buscarEntrada(String nombre)
	{
		Vector<ProductoView> ents = getEntradas();
		for(int i = 0; i < ents.size(); i++)
			if(ents.elementAt(i).getNombre().equals(nombre))
				return ents.elementAt(i);
		return null;
	}
	
	public ProductoView buscarAdicional(String nombre)
	{
		Vector<ProductoView> ads = getAdicionales();
		for(int i = 0; i < ads.size(); i++)
			if(ads.elementAt(i).getNombre().equals(nombre))
				return ads.elementAt(i);
		return null;		
	}
	
	public ProductoView buscarCombo(String nombre)
	{
		Vector<ProductoView> combs = getCombos();
		for(int i = 0; i < combs.size(); i++)
			if(combs.elementAt(i).getNombre().equals(nombre))
				return combs.elementAt(i);
		return null;		
	}

	
	private void test()
	{
		//entradas
		Entrada ent1 = new Entrada("Duro de matar","sala1","22:00",(float)20);
		Entrada ent2 = new Entrada("Jurassic Park","sala 2","19:30",(float)20);
		Entrada ent3 = new Entrada("Pulp Fiction","sala 3","20:40",(float)20);
		Entrada ent4 = new Entrada("North by Northwest","sala 4","17:10",(float)20);
		entradas.add(ent1);
		entradas.add(ent2);
		entradas.add(ent3);
		entradas.add(ent4);
		//adicionales
		Adicional ad1 = new Adicional("pochoclo",(float)80,"comida");
		adicionales.add(ad1);

		Adicional ad2 = new Adicional("coca",(float)40,"bebida");
		adicionales.add(ad2);
		
		//combos
		ArrayList<Producto> pc = new ArrayList<Producto>();
		pc.add(ad1);
		pc.add(ad2);
		ComboPromocional com1 = new ComboPromocional((float)20,"coca+pochoclo",pc);
		combos.add(com1);
	}
}
