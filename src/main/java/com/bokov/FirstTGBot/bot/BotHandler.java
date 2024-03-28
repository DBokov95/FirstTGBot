package com.bokov.FirstTGBot.bot;

import com.bokov.FirstTGBot.models.BTC;
import com.bokov.FirstTGBot.services.BTCService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class BotHandler {

    @Autowired
    private BTCService btcService;
    @Autowired
    private BtcPriceClient btcPriceClient;



    public SendMessage messageHandel(Update update){
        SendMessage message = new SendMessage();
        BotKeyboard keyboard = new BotKeyboard();
        keyboard.sendKeyboard(update.getMessage().getChatId().toString(), message);

        if (update.getMessage().getText().equals("Сегодня")) {
            Date today = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            Date startOfDay = calendar.getTime();
            BTC btc = new BTC();

            List<BTC> btcList = btcService.findAllByRegistrationDateBetween(startOfDay, today);

            if(btcList.isEmpty()){
                btcPriceClient.fetchBitcoinPriceFromExchange();
                btcList = btcService.findAllByRegistrationDateBetween(startOfDay, today);
                btc = btcList.get(btcList.size() - 1);
            }
            else {
                btc = btcList.get(btcList.size() - 1);
                message.setText("Сегодня в " +btc.getDateOfRequest().getHours()+":" + btc.getDateOfRequest().getMinutes() + " биткоин стоил " + btc.getPrise() + "$");
            }

        }
        else if (update.getMessage().getText().equals("Вчера")) {
            Date yesterday = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yesterday);
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.MINUTE,59);
            calendar.set(Calendar.SECOND,59);
            yesterday = calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            Date startOfDay = calendar.getTime();

            System.out.println(startOfDay);
            System.out.println(yesterday);
            BTC btc = new BTC();

            List<BTC> btcList = btcService.findAllByRegistrationDateBetween(startOfDay, yesterday);


            if(btcList.isEmpty()){
                message.setText("В нашей базе нет актуальной информации");
            }
            else {
                btc = btcList.get(btcList.size() - 1);
                message.setText("Вчера в "
                        + btc.getDateOfRequest().getHours() + ":"
                        + btc.getDateOfRequest().getMinutes() + " биткоин стоил " + btc.getPrise() + "$");
            }

        }
        else
            message.setText("Выберете команду");

        return message;
    }




}
