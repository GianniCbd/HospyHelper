package Capstone.HospyHelper.roomType;

import Capstone.HospyHelper.exceptions.NotFoundException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RoomTypeSRV {

    @Autowired
    RoomTypeDAO roomTypeDAO;
    @Autowired
    private Cloudinary cloudinaryUploader;
    public List<RoomType> getAll() {
        return roomTypeDAO.findAll();
    }
    public RoomType saveRoomType(RoomTypeDTO newRt) {
        return roomTypeDAO.save(
                new RoomType(newRt.typeName(), newRt.description(), newRt.image())
        );
    }
    public RoomType getRoomTypeById(Long id) {
        return roomTypeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public RoomType updateRoomType(Long id, RoomTypeDTO rt) {
        RoomType existingRoomType = roomTypeDAO.findById(id).orElseThrow(() -> new NotFoundException("Room not found with ID: " + id));
        existingRoomType.setTypeName(rt.typeName());
        existingRoomType.setDescription(rt.description());
        return roomTypeDAO.save(existingRoomType);
    }
    public void deleteRoomType(Long id) {
        RoomType rt = this.getRoomTypeById(id);
        roomTypeDAO.delete(rt);
    }

    public String UploadImage(Long id,MultipartFile image) throws IOException {
        RoomType found = getRoomTypeById(id);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImage(url);
        return url;
    }
}
