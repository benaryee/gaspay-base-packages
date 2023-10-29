package com.rancard.ussdapp.utils;

import com.rancard.ussdapp.model.enums.MenuKey;
import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.mongo.UssdMenu;
import com.rancard.ussdapp.model.payload.DispatchObject;
import com.rancard.ussdapp.model.payload.Option;
import com.rancard.ussdapp.services.UssdMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.rancard.ussdapp.model.enums.MenuKey.INVALID_USER_SELECTION_RESPONSE;

@Component
@RequiredArgsConstructor
@Slf4j
public class MenuUtils {


    private final UssdMenuService ussdMenuService;

    public String getResponse(DispatchObject dispatchObject , String sessionId){
        UssdMenu menu = getMenu(dispatchObject , sessionId);
        String response = menu.getResponse();
        if(menu.getOptions() != null && menu.getOptions().size() > 0){
            String optionList = stringifyOptionsList(menu.getOptions() , dispatchObject , sessionId);
            response += "\n" + optionList;
        }
        return response;
    }

    public String getResponse(MenuKey menuKey , DispatchObject dispatchObject , String sessionId){
        UssdMenu menu = getMenu(menuKey , sessionId);
        String response = menu.getResponse();
        if(menu.getOptions() != null && menu.getOptions().size() > 0){

            if(dispatchObject.getSession().getOptions() != null && dispatchObject.getSession().getOptions().size() > 0){
                dispatchObject.getSession().setPreviousOptions(dispatchObject.getSession().getOptions());
            }

            String optionList = stringifyOptionsList(menu.getOptions() , dispatchObject , sessionId);
            response += "\n" + optionList;
        }else{
            if(menuKey != INVALID_USER_SELECTION_RESPONSE){
                if(dispatchObject.getSession().getOptions() != null && dispatchObject.getSession().getOptions().size() > 0){
                    dispatchObject.getSession().setPreviousOptions(dispatchObject.getSession().getOptions());
                }

                dispatchObject.getSession().setOptions(null);
            }
        }

        return response;
    }

    public String getResponse(MenuKey menuKey , DispatchObject dispatchObject , String sessionId, List<Option> options){
        UssdMenu menu = getMenu(menuKey , sessionId);
        String response = menu.getResponse();
        if(options != null && options.size() > 0){
            if(dispatchObject.getSession().getOptions() != null && dispatchObject.getSession().getOptions().size() > 0){
                dispatchObject.getSession().setPreviousOptions(dispatchObject.getSession().getOptions());
            }

            String optionList = stringifyOptionsList(options , dispatchObject , sessionId);
            response += "\n" + optionList;
        }else{
            if(menuKey != INVALID_USER_SELECTION_RESPONSE){
                if(dispatchObject.getSession().getOptions() != null && dispatchObject.getSession().getOptions().size() > 0){
                    dispatchObject.getSession().setPreviousOptions(dispatchObject.getSession().getOptions());
                }

                dispatchObject.getSession().setOptions(null);
            }
        }

        return response;
    }

    private UssdMenu getMenu(MenuKey menuKey , String sessionId){
        log.info("[{}] about to get menu response for level : {}", sessionId , menuKey);
        return ussdMenuService.getMenuByLevel(menuKey , sessionId);
    }
    private UssdMenu getMenu(DispatchObject dispatchObject , String sessionId){
      log.info("[{}] about to get menu response for level : {}", sessionId , dispatchObject.getSession().getMenuLevel());
      return ussdMenuService.getMenuByLevel(dispatchObject.getSession().getMenuKey() , sessionId);
    }

    private String stringifyOptionsList(List<Option> options, DispatchObject dispatchObject, String sessionId){

        if(dispatchObject.getSession().getOptionsCurrentPage() < dispatchObject.getSession().getPageSize()
                && dispatchObject.getSession().getOptionsSize() > dispatchObject.getSession().getPageLimit()){
            dispatchObject.getSession().setHasNext(true);
        }

        if(dispatchObject.getSession().getOptionsCurrentPage() > 1){
            dispatchObject.getSession().setHasBack(true);
        }

        StringBuilder optionsList = new StringBuilder();

        int counter = 1;
        for (Option option : options) {
            optionsList.append(counter).append(". ").append(WordUtils.capitalize(option.getContent())).append("\n");
            dispatchObject.getSession().getOptions().put(counter,option.getContent());
            counter++;
        }

        if (dispatchObject.getSession().isHasBack()){
            optionsList.append("0. Back").append("\n");
        }

        if(dispatchObject.getSession().getOptionsCurrentPage() != 0 && dispatchObject.getSession().isHasNext()){
            optionsList.append("9. Next");
        }

        return optionsList+"";
    }

}
