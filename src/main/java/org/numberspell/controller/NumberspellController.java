package org.numberspell.controller;

import org.numberspell.data.Dictionary;
import org.numberspell.model.CombinationsGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NumberspellController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/search", params = "number")
    public String searchResults(@RequestParam ("number") String number, ModelMap modelMap) {

        boolean isInputValid = true;
        String invalidInputMessage = "Enter number here...";


        //Invalid entry case: too many digits entered
        if(number.length() > 10) {
            isInputValid = false;
            invalidInputMessage = "A maximum of 10 digits can be entered. Please try again...";
        } else {
            //Invalid entry case: chars other than 2-9 entered
            char[] numberArray = number.toCharArray();
            for(char c : numberArray) {
                if(c != '2' &&
                        c != '3' &&
                        c != '4' &&
                        c != '5' &&
                        c != '6' &&
                        c != '7' &&
                        c != '8' &&
                        c != '9') {
                    isInputValid = false;
                    invalidInputMessage = "Only digits 2 - 9 are valid. Please try again...";
                }
            }
        }

        List<String> phonewords;
        List<String> validPhonewords;
        if(isInputValid == true) {
            //generating phonewords
            CombinationsGenerator generator = new CombinationsGenerator(number);
            phonewords = generator.getPhonewords();

            //parsing valid phonewords
//            validPhonewords = new ArrayList<>(phonewords);
//            for(String word : phonewords) {
//                if(!Dictionary.getDictionaryWordList().contains(word)) {
//                    validPhonewords.remove(word);
//                }
//            }

            boolean compoundWordFound = false;
            validPhonewords = new ArrayList<>(phonewords);
            for(int index = 0; index < phonewords.size(); index++) {
                String w = phonewords.get(index);
                //If string is in dictionary then no need to remove that string
                if(Dictionary.getDictionaryWordList().contains(w)) {
                    continue;
                }

                String compoundWord = w;
                compoundWordFound = false;

                //For bigger strings, we look for more than one word in that string
                if(w.length() > 5) {
                    for(int i = 1; i < w.length()-2; i++) {
                        String temp1 = w.substring(0, i);
                        String temp2 = w.substring(i, w.length());
                        if(Dictionary.getDictionaryWordList().contains(temp1)) {
                            if(Dictionary.getDictionaryWordList().contains(temp2)) {
                                compoundWord = temp1 + "-" + temp2;
                                validPhonewords.set(validPhonewords.indexOf(w), compoundWord);
                                compoundWordFound = true;
                                break;
                            }
                        }
                    }
                }

                if(!compoundWordFound) {
                    validPhonewords.remove(w);
                }

            }



        } else {
            validPhonewords = new ArrayList<>();
            phonewords = new ArrayList<>();
        }


        //the following line of code will make the words list available to the html
        modelMap.put("validPhonewords", validPhonewords);
        modelMap.put("inputMessage", invalidInputMessage);
        modelMap.put("numberOfWords", validPhonewords.size());
        modelMap.put("numberOfCombinations", phonewords.size());
        modelMap.put("numberEntered", number);

        return "search-results";
    }



    @RequestMapping("/downloads")
    @ResponseBody
    public String downloads() {
        return "This page is under construction.";
    }



    @RequestMapping("/faqs")
    @ResponseBody
    public String faqs() {
        return "This page is under construction.";
    }



    @RequestMapping("/tips")
    @ResponseBody
    public String tips() {
        return "This page is under construction.";
    }

}
