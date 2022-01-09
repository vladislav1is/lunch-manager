package com.redfox.lunchmanager;

import java.time.LocalDate;

public interface HasNameAndDate extends HasIdAndName {
    LocalDate getRegistered();
}
