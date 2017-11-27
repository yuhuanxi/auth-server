package com.ysp.demo.admin;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import de.codecentric.boot.admin.notify.MailNotifier;
import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import de.codecentric.boot.admin.notify.filter.FilteringNotifier;

/**
 * 功能描述.
 *
 * @author shipengfish
 * @version V1.0
 * @date 2017-11-27 15:29
 * @since V1.0
 */
@Configuration
@EnableScheduling
@Profile({"secure"})
@AutoConfigureAfter({de.codecentric.boot.admin.config.NotifierConfiguration.MailNotifierConfiguration.class})
public class NotifierConfiguration {
  private static final String[] ignoreChanges = {"*:UP", "UNKNOWN:OFFLINE"};
  @Autowired
  private MailNotifier mailNotifier;

  @Bean
  @Primary
  public RemindingNotifier remindingNotifier() {
    mailNotifier.setIgnoreChanges(ignoreChanges);
    RemindingNotifier notifier = new RemindingNotifier(filteringNotifier(mailNotifier));
    notifier.setReminderPeriod(TimeUnit.HOURS.toMillis(2));
    return notifier;
  }

  @Scheduled(fixedRate = 1800_000L)
  public void remind() {
    remindingNotifier().sendReminders();
  }

  @Bean
  public FilteringNotifier filteringNotifier(Notifier delegate) {
    return new FilteringNotifier(delegate);
  }

}
