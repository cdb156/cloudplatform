package com.xyz.cloudplatform.controller;


import com.alibaba.dubbo.common.json.JSON;
import com.xyz.cloudplatform.model.ProductBrand;
import com.xyz.cloudplatform.model.ProductCategory;
import com.xyz.cloudplatform.model.ProductMain;
import com.xyz.cloudplatform.service.ProductService;
import com.xyz.cloudplatform.vo.PersonVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/10.
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    /**
     * 主界面
     *
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        logger.warn("debug msg");
        logger.info("主界面");
        return new ModelAndView("product/main");
    }

    /**
     * 类别列表
     *
     * @return
     */
    @RequestMapping(value = "type/list", method = RequestMethod.GET)
    public ModelAndView listType() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ProductCategory> list = productService.findAllCategory();
        map.put("categorylist", list);
        return new ModelAndView("product/type_list", map);
    }

    /**
     * 类别添加
     *
     * @return
     */
    @RequestMapping(value = "type/add", method = RequestMethod.POST)
    public ModelAndView addType(HttpServletRequest request) {

        String code = request.getParameter("code");
        String name = request.getParameter("name");

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCode(code);
        productCategory.setName(name);
        productCategory.setpCode("000");
        productService.saveCategory(productCategory);
        return new ModelAndView("redirect:list");
    }

    /**
     * 品牌列表
     *
     * @return
     */
    @RequestMapping(value = "brand/list", method = RequestMethod.GET)
    public ModelAndView listBrand() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ProductBrand> list = productService.findAllBrand();
        map.put("brandlist", list);
        return new ModelAndView("product/brand_list", map);
    }

    /**
     * 品牌添加
     *
     * @return
     */
    @RequestMapping(value = "brand/add", method = RequestMethod.POST)
    public ModelAndView addBrand(HttpServletRequest request) {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String place = request.getParameter("place");
        ProductBrand productBrand = new ProductBrand();
        productBrand.setbCode(code);
        productBrand.setbName(name);
        productBrand.setbPlace(place);
        productService.saveBrand(productBrand);
        return new ModelAndView("redirect:list");
    }


    /**
     * 商品添加页面
     *
     * @return
     */
    @RequestMapping(value = "edit")
    public ModelAndView editProduct() {
        List<ProductCategory> categories = productService.findAllCategory();
        List<ProductBrand> brands = productService.findAllBrand();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("categories", categories);
        map.put("brands", brands);
        return new ModelAndView("product/product_edit", map);
    }

    /**
     * 商品列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView listProduct() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ProductMain> list = productService.findAllProduct();
        map.put("productlist", list);
        return new ModelAndView("product/product_list", map);
    }

    /**
     * 商品添加
     *
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addProduct(@ModelAttribute("ProductMain") ProductMain productMain) {
        productService.saveProduct(productMain);
        return new ModelAndView("redirect:list");
    }

    /**
     * 商品添加
     *
     * @return
     */
    @RequestMapping(value = "tx", method = RequestMethod.GET)
    public ModelAndView tx() {
        return new ModelAndView("product/tx");
    }


    /**
     * 事务测试0
     *
     * @return
     */
    @RequestMapping(value = "tx/add", method = RequestMethod.POST)
    public ModelAndView txAdd(HttpServletRequest request) {
        String t_code = request.getParameter("t_code");
        String t_name = request.getParameter("t_name");


        String p_code = request.getParameter("p_code");
        String p_name = request.getParameter("p_name");
        String place = request.getParameter("place");


        ProductBrand productBrand = new ProductBrand();
//        productBrand.setbCode(p_code);
        productBrand.setbName(p_name);
        productBrand.setbPlace(place);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCode(t_code);
        productCategory.setName(t_name);
        productCategory.setpCode("000");

        productService.txAdd(productCategory,productBrand);
//        productService.saveBrand(productBrand);

        return new ModelAndView("redirect:/product/type/list");
    }

    @RequestMapping(value = "personVo", method = RequestMethod.GET)
    @ResponseBody
    public PersonVo personVo() {
       PersonVo personVo = new PersonVo();
        personVo.setName("张三");
        personVo.setSex("男");
        personVo.setMobile("15135158097");

        return personVo;
    }

    @RequestMapping(value = "personRequest", method = RequestMethod.POST)
    @ResponseBody
    public String personRequest(@RequestBody PersonVo personVo) {
        try {
            System.out.println(JSON.json(personVo));
        }catch (Exception e){
            logger.info("异常",e);
        }
        return "success";
    }

}
