package app;



import java.util.ArrayList;
import java.util.List;

import org.dom4j.util.ProxyDocumentFactory;
import org.hibernate.SessionFactory;

import bean.Adicional;
import bean.Producto;
import bean.Venta;
import bean.ComboPromocional;
import bean.Entrada;
import bean.ItemVenta;
import bean.srv.ProductoSrv;
import bean.srv.VentaSRV;
import hbt.HibernateUtil;

public class cargaDatos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sf = null;
		sf = HibernateUtil.getSessionFactory();
		ArrayList<Producto> productos = new ArrayList<Producto>();
		
		
	
		Entrada e = new Entrada("Star Wars", "Sala 2", "16:15", 120);
		productos.add(e);
		Entrada e2 = new Entrada("Barney", "Sala 3", "18:15", 167);
		productos.add(e2);
		Entrada e4 = new Entrada("One Piece", "Sala 5", "16:59", 128);
		productos.add(e4);
		ProductoSrv.grabarProducto(e);
		ProductoSrv.grabarProducto(e2);
		ProductoSrv.grabarProducto(e4);
		Entrada ent1 = new Entrada("Duro de matar","Sala 1","22:00",(float)20);
		Entrada ent2 = new Entrada("Jurassic Park","Sala 2","19:30",(float)20);
		Entrada ent3 = new Entrada("Pulp Fiction","Sala 3","20:40",(float)20);
		Entrada ent4 = new Entrada("North by Northwest","Sala 4","17:10",(float)20);
		ProductoSrv.grabarProducto(ent1);
		ProductoSrv.grabarProducto(ent2);
		ProductoSrv.grabarProducto(ent3);
		ProductoSrv.grabarProducto(ent4);
		
		ArrayList<Entrada> entraditas = (ArrayList<Entrada>) ProductoSrv.leerEntradas();

		//adicionales
		Adicional ad2 = new Adicional("Coca",(float)40,"Bebida");
		ProductoSrv.grabarProducto(ad2);
		ad2 = new Adicional("Fanta",15,"Bebida");
		ProductoSrv.grabarProducto(ad2);
		ad2 = new Adicional("Pochoclo",(float)80,"Comida");
		ProductoSrv.grabarProducto(ad2);
		ad2 = new Adicional("Pizza",(float)80,"Comida");
		ProductoSrv.grabarProducto(ad2);

		
		ad2 = new Adicional("Remera Ironman",(float)40,"Merchandising");
		ProductoSrv.grabarProducto(ad2);
		ad2 = new Adicional("Martillo Thor",(float)40,"Merchandising");
		ProductoSrv.grabarProducto(ad2);
		ad2 = new Adicional("Gorra",(float)40,"Merchandising");
		ProductoSrv.grabarProducto(ad2);
		
		//combos
		ArrayList<Producto> pc = new ArrayList<Producto>();
		pc.add(ProductoSrv.getProducto(8));
		pc.add(ProductoSrv.getProducto(10));
		ComboPromocional com1 = new ComboPromocional((float)20,"Coca + Pochoclo",pc);
		ProductoSrv.grabarProducto(com1);
		
		ArrayList<Producto> pc2 = new ArrayList<Producto>();
		pc2.add(ProductoSrv.getProducto(9));
		pc2.add(ProductoSrv.getProducto(11));
		ComboPromocional com2 = new ComboPromocional((float)10,"Fanta + Pochoclo",pc2);
		ProductoSrv.grabarProducto(com2);
		
		ArrayList<Producto> pc3 = new ArrayList<Producto>();
		pc3.add(ProductoSrv.getProducto(12));
		pc3.add(ProductoSrv.getProducto(14));
		ComboPromocional com3 = new ComboPromocional((float)30,"Remera + Gorra",pc3);
		ProductoSrv.grabarProducto(com3);
		
		Venta venta = new Venta();
		venta.agregarItem(ProductoSrv.getProducto(1), 99);
		VentaSRV.grabarVenta(venta);
		ArrayList<Venta> v = (ArrayList<Venta>)VentaSRV.leerVentas();
	}
}