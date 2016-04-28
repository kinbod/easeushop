package org.networking.web.controller;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.networking.entity.Member;
import org.networking.entity.SalesItem;
import org.networking.entity.SalesOrder;
import org.networking.repository.SalesOrderRepository;
import org.networking.service.AccountPointsService;
import org.networking.service.MemberService;
import org.networking.service.ProductService;
import org.networking.service.SalesOrderService;
import org.networking.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin/order")
public class OrderController extends BaseController<SalesOrder> {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountPointsService accountPointsService;
    
    @Autowired
	private SalesOrderRepository salesOrderRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String getView(Model model) {
    	NumberFormat n = NumberFormat.getCurrencyInstance(); 
    	model.addAttribute("totalSales", (salesOrderRepository.getTotalSales()==null?0:(n.format(salesOrderRepository.getTotalSales()).replace("$", "Php "))));
    	model.addAttribute("weeklyTotalSales", (salesOrderRepository.getWeekTotalSales()==null?0:(n.format(salesOrderRepository.getWeekTotalSales()).replace("$", "Php "))));
        return "admin-order";
    }

    @Override
    protected void preCreate(SalesOrder salesOrder) {
        Long sellerId = salesOrder.getSellerId();
        Member member = memberService.load(sellerId);
        salesOrder.setSeller(member);

        for(SalesItem item : salesOrder.getItems()) {
            item.setProduct(productService.load(item.getProductId()));
            item.setCreateDate(new Date());
            item.setUpdateDate(new Date());
        }
        
        if(salesOrder.getOrderDateString() != null) {
        	try {
				salesOrder.setOrderDate(dateFormat.parse(salesOrder.getOrderDateString()));
			} catch (ParseException e) {
			}
        }
    }

    @Override
    protected void postCreate(SalesOrder salesOrder) {
        //Create account points for the sales order
        accountPointsService.createForProduct(salesOrder, salesOrder.getOrderDate());
    }
    
    @RequestMapping(value = "/list", produces = {"application/json"})
    public @ResponseBody List<SalesOrder> list() {
        return ((SalesOrderService)baseService).getAllOrdersOrderByDate();
    }
}