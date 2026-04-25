import os
from PIL import Image
import numpy as np

DEBUG_DIR = "debug_images"

def save_debug_images(digits, predictions):
    os.makedirs(DEBUG_DIR, exist_ok=True)
    
    datetime_str = __import__('datetime').datetime.now().strftime("%Y-%m-%d-%Hh%Mm%Ss")
    os.makedirs(f"{DEBUG_DIR}/{datetime_str}", exist_ok=True)        
    
    for i, (digit, pred) in enumerate(zip(digits, predictions)):
        # digit est un tensor [1, 1, 28, 28]
        # on le convertit en image PIL
        img_array = digit.squeeze().numpy()
        # on remet les valeurs entre 0 et 255
        img_array = ((img_array * 0.5) + 0.5) * 255
        img_array = img_array.astype(np.uint8)
        
        img = Image.fromarray(img_array)       
        
        filename = f"{DEBUG_DIR}/{datetime_str}/digit_{i}_pred_{pred['predicted_class']}_conf_{pred['confidence']:.2f}.png"
        img.save(filename)