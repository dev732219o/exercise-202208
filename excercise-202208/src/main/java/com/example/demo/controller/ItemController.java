package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Item;
import com.example.demo.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	//itemsにGETでリクエストした場合
	@GetMapping
	public String index(Model model) {
        logger.debug("message debug index");
		//Model型のmodelにitemsをつめることでControllerからView(Tymleaf)へデータを受け渡せる。
		model.addAttribute("items", itemService.findAll());
		return "index"; //戻り値としてindex.htmlを表示する
	}
	
	@GetMapping("{id}")
	public String show(@PathVariable Long id, Model model) {//PathVariableでURLに含まれる動的なパラメータを受け取る
        logger.debug("message debug id");
		model.addAttribute("item", itemService.findOne(id));
		return "show";
	}
	@GetMapping("new")
	public String newItem(@ModelAttribute("item") Item item, Model model) {
		// リクエストパラメーターをItemに詰める
		// GETリクエストのようにデータが渡ってこない場合は new Item() の状態の空のオブジェクトが生成されるようだ
		return "new";
	}
	
	@GetMapping("{id}/edit")
	public String edit(@PathVariable Long id, @ModelAttribute("item") Item item, Model model) {
        logger.debug("message debug {id}/edit");
		model.addAttribute("item", itemService.findOne(id));
		return "edit";
	}
	
	@PostMapping
	public String create(@ModelAttribute("item") @Validated Item item, BindingResult result, Model model) {
		//Serviceで規定したValidateを実施しかつ結果をresultに格納する
		if(result.hasErrors()) {
			return "new";
		} else {
	        logger.debug("message debug create");
			itemService.save(item);
			return "redirect:/items";
		}
	}
	@PutMapping("{id}")
	public String update(@PathVariable Long id, @ModelAttribute("item") @Validated Item item, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("item", item);
	        logger.error("message debug update error");
			return "edit";
		} else {
			item.setId(id);
			itemService.update(item);
	        logger.debug("message debug update");
			return "redirect:/items";
		}
	}
	@DeleteMapping("{id}")
	public String delete(@PathVariable Long id) {
		itemService.delete(id);
		return "redirect:/items";
	}	
}
