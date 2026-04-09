from torchvision import transforms
from PIL import Image, ImageOps


def preprocess_image(imageBase, image_size=(28, 28)):    
    image = imageBase.convert('L')
    image = ImageOps.invert(image)
    
    transform = transforms.Compose([
        transforms.Resize(image_size),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.5], std=[0.5])
    ])
    image_tensor = transform(image)
    
    return image_tensor.unsqueeze(0)