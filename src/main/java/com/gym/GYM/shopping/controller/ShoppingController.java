package com.gym.GYM.shopping.controller;

import com.gym.GYM.shopping.dto.BasketDTO;
import com.gym.GYM.shopping.dto.OrdersDTO;
import com.gym.GYM.shopping.dto.PayDTO;
import com.gym.GYM.shopping.dto.ProductDTO;
import com.gym.GYM.shopping.dto.WishDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.gym.GYM.shopping.service.ShoppingService;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ShoppingController {

    List<OrdersDTO> basketList = new ArrayList<OrdersDTO>();
    @Autowired
    private ShoppingService shoppingsvc;

    private ModelAndView mav = new ModelAndView();
    private boolean basketRegi1;


    // shoppionWishFrom : 찜한상품 보기 페이지
    @GetMapping("/shoppingWishForm")
    private ModelAndView shoppingWishForm(@RequestParam String memberId) {
        mav = shoppingsvc.shoppingWishForm(memberId);
        return mav;
    }

    //shoppingList
    @GetMapping("/shoppingMainForm")
    private ModelAndView shoppingList() {
        mav = shoppingsvc.shoppingList();
        return mav;
    }

    // shoppingView
    @GetMapping("/shoppingView")
    private ModelAndView shoppingView(@RequestParam(value = "productCode") String productCode) {

        mav = shoppingsvc.shoppingView(productCode);
        return mav;
    }

    //basketRegist: 찜 상품 장바구니에 담고 담겨있는 상품목록 가져오는 메소드
    //오탈자 수정중 오류 발생하여 앞에 wish 추가 병합할 때까지 남겨놓기
    @PostMapping("/basketRegist")
    private @ResponseBody List<OrdersDTO> wishBasketRegist(@RequestParam String productCode, @RequestParam String memberId) {

        basketList = shoppingsvc.basketList(productCode, memberId);
        return basketList;
    }

    //basketForm : 장바구니로 이동하는 메소드
    @GetMapping("/basketForm")
    private String basketForm() {
        return "Shopping/ShoppingBasket";
    }


    //myBasketListAjax :내 장바구니 불러오는 ajax ShoppingBasket.html에서 사용
    @GetMapping("/myBasketListAjax")
    private @ResponseBody List<BasketDTO> myBasketListAjax(@RequestParam String memberId) {
        List<BasketDTO> myBasketListAjax = new ArrayList<BasketDTO>();
        myBasketListAjax = shoppingsvc.myBasketListAjax(memberId);
        return myBasketListAjax;
    }

    // shoppingHistory : 주문내역
    @GetMapping("/shoppingHistory")
    private ModelAndView shoppingHistoryList(@RequestParam String payId) {
        mav = shoppingsvc.shoppingHistoryList(payId);
        return mav;
    }


    // shoppingPaymentList : 결제페이지 리스트
    @GetMapping("/shoppingPayment")
    private ModelAndView shoppingPaymentList(@RequestParam String orderId) {
        mav = shoppingsvc.shoppingPaymentList(orderId);
        return mav;
    }

    //basketInquire :상품이 장바구니에 있는지 확인하는 메소드
    @PostMapping("/basketInquire")
    private @ResponseBody List<String> basketInquire(@RequestParam String memberId, @RequestParam String productCode) {
        List<String> basketInquire = new ArrayList<>();
        basketInquire = shoppingsvc.basketInquire(memberId, productCode);

        return basketInquire;
    }

    //basketDelete orders 목록에서 지우기
    @PostMapping("/basketDelete")
    private @ResponseBody List<String> basketDelete(@RequestParam String memberId, @RequestParam String productCode) {
        List<String> basketDelete = new ArrayList<>();
        basketDelete = shoppingsvc.basketDelete(memberId, productCode);
        return basketDelete;
    }

    //basketRegistAjax 장바구니 목록에 추가하기
    @PostMapping("/basketRegistAjax")
    private @ResponseBody List<String> basketRegist(@RequestParam String memberId, @RequestParam String productCode, @RequestParam String orderPrice, @RequestParam String orderName) {
        List<String> basketInquire = new ArrayList<>();
        basketInquire = shoppingsvc.basketRegistAjax(memberId, productCode, orderPrice, orderName);
        return basketInquire;
    }


    //wishInquire :상품이 wish에 있는지 확인하는 메소드

    @PostMapping("/wishInquire")
    private @ResponseBody List<String> wishInquire(@RequestParam String memberId, @RequestParam String productCode) {

        List<String> wishInquire = new ArrayList<String>();
        wishInquire = shoppingsvc.wishInquire(memberId, productCode);
        return wishInquire;
    }

    //wishDelete wish 목록에서 지우기
    @PostMapping("/wishDelete")
    private @ResponseBody List<String> wishDelete(@RequestParam String memberId, @RequestParam String productCode) {
        List<String> wishDelete = new ArrayList<>();
        wishDelete = shoppingsvc.wishDelete(memberId, productCode);
        return wishDelete;
    }

    @PostMapping("/wishRegist")
    private @ResponseBody List<String> wishRegist(@RequestParam String memberId, @RequestParam String productCode) {
        List<String> wishInquire = new ArrayList<>();
        wishInquire = shoppingsvc.wishRegist(memberId, productCode);
        return wishInquire;
    }

    //basketOrdersPriceUpdate: 장바구니에서 수량 선택시 orderPrice 업데이트 하는 문
    @PostMapping("/basketOrdersPriceUpdate")
    private @ResponseBody List<OrdersDTO> basketOrdersPriceUpdate(@RequestParam String memberId, @RequestParam String productCode, @RequestParam String orderPrice) {
        List<OrdersDTO> basketListUpdate = new ArrayList<OrdersDTO>();
        basketListUpdate = shoppingsvc.basketOrdersPriceUpdate(memberId, productCode, orderPrice);
        return basketListUpdate;
    }

    //addressInputAjax : 이전주소 불러오는 ajax
    @GetMapping("/addressInputAjax")
    private @ResponseBody List<String> addressInputAjax(@RequestParam String memberId) {
        List<String> addressInputAjax = new ArrayList<String>();
        addressInputAjax = shoppingsvc.addressInputAjax(memberId);


        return addressInputAjax;
    }

    //basketPayment : 모달로 주문시 orders 테이블 업데이트 하는 메소드
    @PostMapping("/basketPayment")
    private ModelAndView basketPayment(@RequestParam String memberId, @RequestParam String addr, @RequestParam String coment) {
        System.out.println("컨트롤러 요청사항:" + coment);
        System.out.println("컨트롤러 주소:" + addr);
        mav = shoppingsvc.basketPayment(memberId, addr, coment);
        return mav;
    }

    @PostMapping("/payRegist")
    private @ResponseBody List<PayDTO> payRegist(@ModelAttribute PayDTO pay){
        List<PayDTO> payList = shoppingsvc.payRegist(pay);

        return payList;
    }


    //orderCountOutputAjax : 장바구니에서 상품 수량 표시해주는 ajax

    @GetMapping("/orderCountOutputAjax")
    private @ResponseBody List<String> orderCountOutputAjax(@RequestParam String memberId, @RequestParam String productCode) {
        List<String> orderCountOutputAjax = new ArrayList<String>();
        orderCountOutputAjax = shoppingsvc.orderCountOutputAjax(memberId, productCode);
        return orderCountOutputAjax;
    }

//orderCountPlus: 장바구니에서 상품 수량 변경시 orders 테이블 count + 해주는 메소드
    @PostMapping("/orderCountPlus")
    private @ResponseBody List<String> orderCountPlus (@RequestParam String memberId, @RequestParam String productCode, @RequestParam String orderPrice){
        System.out.println(productCode);
        List<String> orderCountPlus =new ArrayList<>();
        orderCountPlus=shoppingsvc.orderCountPlus(memberId,productCode,orderPrice);

        return orderCountPlus;
    }

}
