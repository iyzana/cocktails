package de.randomerror.cocktails.backend.controller;

import de.randomerror.cocktails.backend.App;
import de.randomerror.cocktails.backend.dao.OrderDao;
import de.randomerror.cocktails.backend.dto.CreateOrderDto;
import de.randomerror.cocktails.backend.entity.CocktailOrder;
import de.randomerror.cocktails.backend.service.InputService;
import de.randomerror.cocktails.backend.service.LoginService;

import static spark.Spark.get;
import static spark.Spark.post;

public class OrderController {
    private static final String ROUTE = "/order";

    public static void registerRoutes() {
        get(ROUTE, (req, res) -> OrderDao.findByRequester(LoginService.getUniqueName(req)), App.gson::toJson);

        post(ROUTE, (req, res) -> {
            String id = LoginService.getUniqueName(req);
            CreateOrderDto createOrder = App.gson.fromJson(req.body(), CreateOrderDto.class);
            CocktailOrder order = new CocktailOrder(id, createOrder.getName(), InputService.buildInputs(createOrder.getInputs()));
            return OrderDao.save(order);
        }, App.gson::toJson);
    }
}
