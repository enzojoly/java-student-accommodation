package uwe.tae.sys.controller;

import uwe.tae.sys.model.Accommodation;
import uwe.tae.sys.model.Hall;


/*
  Interface for handling updates to Hall (manager details) and Accommodation objects.
  This interface is implemented in the SystemController class to pass the
  updated Hall and Accommodation objects.
 */
public interface InformationUpdateCallback {
    void onInformationUpdated(Hall hall, Accommodation accommodation);
}
