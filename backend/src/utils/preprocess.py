from torchvision import transforms
from PIL import Image

from src.utils.segmentation import segment_image


def preprocess_image(imageBase, image_size=(28, 28)):    
    list_chiffres = segment_image(imageBase)
    
    images_tensor = []
    for digit in list_chiffres:
        digit_pil = Image.fromarray(digit)
        
        transform = transforms.Compose([
            transforms.Resize(image_size),
            transforms.ToTensor(),
            transforms.Normalize(mean=[0.5], std=[0.5])
        ])
        digit_tensor = transform(digit_pil)
        images_tensor.append(digit_tensor.unsqueeze(0))
    
    return images_tensor