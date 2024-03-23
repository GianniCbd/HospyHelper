package Capstone.HospyHelper.roomType;

import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.exceptions.NotFoundException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class RoomTypeSRV {

    @Autowired
    RoomTypeDAO roomTypeDAO;
    @Autowired
    private Cloudinary cloudinaryUploader;


    public Page<RoomType> getAllRoomsTypesByOwnerId(UUID userId, int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return roomTypeDAO.findByOwnerId(userId,pageable);
    }

    public RoomType saveRoomType(RoomTypeDTO newRt, User user) {
        return roomTypeDAO.save(
                new RoomType(newRt.typeName(), newRt.description(), newRt.image(),user)
        );
    }
    public RoomType getRoomTypeById(Long id) {
        return roomTypeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public RoomType updateRoomType(Long id, RoomTypeDTO rt) {
        RoomType existingRoomType = roomTypeDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found with ID: " + id));
        existingRoomType.setTypeName(rt.typeName());
        existingRoomType.setDescription(rt.description());
        existingRoomType.setImage(rt.image());
        return roomTypeDAO.save(existingRoomType);
    }
    public String UploadImage(Long id, MultipartFile image) throws IOException {
        RoomType found = getRoomTypeById(id);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImage(url);
        return url;
    }

    public void deleteRoomType(Long id) {
        RoomType rt = this.getRoomTypeById(id);
        roomTypeDAO.delete(rt);
    }

}