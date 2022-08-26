package com.example.demo.controller;

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
	
	//itemsにGETでリクエストした場合
	@GetMapping
	public String index(Model model) {
		//Model型のmodelにitemsをつめることでControllerからView(Tymleaf)へデータを受け渡せる。
		System.out.println("item index / controller");
		model.addAttribute("items", itemService.findAll());
		return "index"; //戻り値としてindex.htmlを表示する
	}
	
	@GetMapping("{id}")
	public String show(@PathVariable Long id, Model model) {//PathVariableでURLに含まれる動的なパラメータを受け取る
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
		System.out.println("item edit / controller");
		model.addAttribute("item", itemService.findOne(id));
		return "edit";
	}
	
	@PostMapping
	public String create(@ModelAttribute("item") @Validated Item item, BindingResult result, Model model) {
		//Serviceで規定したValidateを実施しかつ結果をresultに格納する
		if(result.hasErrors()) {
			return "new";
		} else {
			System.out.println("item saving / controller");
			itemService.save(item);
			return "redirect:/items";
		}
	}
	@PutMapping("{id}")
	public String update(@PathVariable Long id, @ModelAttribute("item") @Validated Item item, BindingResult result, Model model) {
		System.out.println("item updating 0 / controller");
		if(result.hasErrors()) {
			model.addAttribute("item", item);
			return "edit";
		} else {
			System.out.println("item updating 1 / controller");
			item.setId(id);
			itemService.update(item);
			return "redirect:/items";
		}
	}
	@DeleteMapping("{id}")
	public String delete(@PathVariable Long id) {
		itemService.delete(id);
		return "redirect:/items";
	}	
}
