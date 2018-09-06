package app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
import tda.TDASistemaCine;

public class SistemaCine extends UnicastRemoteObject implements TDASistemaCine
{
	private static final long serialVersionUID = 1L;
	private List<Entrada> entradas;
	private List<Adicional> adicionales;
	private List<ComboPromocional> combos;
	private List<Venta> ventas;
	private Venta ventaActual;
	
	//private static SistemaCine instancia = null;
	
	public SistemaCine() throws RemoteException 
	{
		entradas = ProductoSrv.leerEntradas();
		adicionales = ProductoSrv.leerAdicional();
		combos = ProductoSrv.leerCombos();
		ventas = new ArrayList<Venta>();
	}
	
	/*
	public static SistemaCine getInstance() throws RemoteException
	{
		if(instancia == null) 
		{
			instancia = new SistemaCine();
	    }
		return instancia;
	}
	*/
	
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
		for(Producto p : entradas)
			ent.add(p.getView());
		return ent;
	}
	
	public Vector<ProductoView> getAdicionales()
	{
		Vector<ProductoView> ad = new Vector<ProductoView>();
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
	
	public Vector<Vector<String>> getListadoProductos() 
	{
		Vector<Vector<String>> prods = new Vector<Vector<String>>();
		List<Producto> ps = ProductoSrv.leerProductos();
		
		for(Producto p : ps)
		{
			Vector<String> strs = new Vector<String>();
			strs.add(String.valueOf(p.getView().getCodigo()));
			strs.add(p.getNombre());
			strs.add(String.valueOf(p.getPrecio()));
			prods.add(strs);
		}
		return prods;
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
}
