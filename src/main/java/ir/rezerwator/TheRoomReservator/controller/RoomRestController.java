package ir.rezerwator.TheRoomReservator.controller;

import ir.rezerwator.TheRoomReservator.dto.Message;
import ir.rezerwator.TheRoomReservator.dto.Room;
import org.springframework.beans.factory.annotation.Autowired;
import ir.rezerwator.TheRoomReservator.service.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/organizations/{id}/rooms")
@RestController
public class RoomRestController {

    private final RoomService roomService;

    @Autowired
    public RoomRestController(RoomService roomService){
        this.roomService = roomService;
    }

    @PostMapping
    public Room create (@Valid @RequestBody Room room, @PathVariable("id") int idOrganization){
        return roomService.create(room, idOrganization);
   }

    @GetMapping("/{roomId}")
    public Room readById(@PathVariable("roomId") int id, @PathVariable("id") int idOrganization){
        return roomService.readById(id, idOrganization);
    }

   @GetMapping()
    public List<Room> readAll(@PathVariable("id") int idOrganization) {
        return roomService.readAll(idOrganization);
   }

    @PutMapping("/{roomId}")
    public Room update (@PathVariable("roomId") int id, @PathVariable("id") int idOrganization,
                            @Valid @RequestBody Room room){
        return roomService.update(room, id, idOrganization);
    }

    @DeleteMapping("/{roomId}")
    public Message delete(@PathVariable("roomId") int id, @PathVariable("id") int idOrganization){
        roomService.delete(id, idOrganization);
        return new Message("The room was successfully deleted.");
    }
}
