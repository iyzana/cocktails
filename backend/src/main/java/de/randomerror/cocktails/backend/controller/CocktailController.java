package de.randomerror.cocktails.backend.controller;


import de.randomerror.cocktails.backend.App;
import de.randomerror.cocktails.backend.dao.CocktailDao;
import de.randomerror.cocktails.backend.dto.CreateCocktailDto;
import de.randomerror.cocktails.backend.entity.Cocktail;
import de.randomerror.cocktails.backend.exception.NotFoundException;
import de.randomerror.cocktails.backend.service.InputService;
import de.randomerror.cocktails.backend.service.LoginService;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class CocktailController {
    private static final String ROUTE = "/cocktail";

    public static void registerRoutes() {
        get(ROUTE, (req, res) -> CocktailDao.findAll(), App.gson::toJson);

        post(ROUTE, (req, res) -> {
            CreateCocktailDto create = App.gson.fromJson(req.body(), CreateCocktailDto.class);
            Cocktail c = new Cocktail(create.getName(), InputService.buildInputs(create.getInputs()));
            return CocktailDao.save(c);
        }, App.gson::toJson);

        get(ROUTE + "/:id", (req, res) -> {
            int id = parseInt(req.params("id"));
            return CocktailDao.findById(id)
                    .orElseThrow(() -> new NotFoundException("cocktail", id));
        }, App.gson::toJson);

        delete(ROUTE + "/:id", (req, res) -> {
            LoginService.requireAdmin(req);
            int id = parseInt(req.params("id"));
            Cocktail cocktail = CocktailDao.findById(id)
                    .orElseThrow(() -> new NotFoundException("cocktail", id));
            CocktailDao.delete(cocktail);

            res.status(204);
            return null;
        }, App.gson::toJson);
    }

}
