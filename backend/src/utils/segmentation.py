import cv2
import numpy as np
from PIL import Image

def segment_image(image):
    
    # Conversion en array numpy puis niveaux de gris
    image_np = np.array(image)
    gray = cv2.cvtColor(image_np, cv2.COLOR_RGB2GRAY)
    
    # Binarisation
    ret, thresh = cv2.threshold(gray, 0, 255, cv2.THRESH_OTSU)
    
    # Détection des contours
    contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    
    # Tri de gauche à droite
    contours = sorted(contours, key=lambda c: cv2.boundingRect(c)[0])
    
    # Filtrer les contours trop petits (bruit)
    min_area = thresh.shape[0] * thresh.shape[1] * 0.01  # 1% de l'image
    contours = [c for c in contours if cv2.contourArea(c) > min_area]
    
    # Si un seul chiffre, on retourne l'image entière
    if len(contours) <= 1:
        return [thresh]
    
    # Sinon on découpe chaque chiffre
    padding = int(min(thresh.shape[0], thresh.shape[1]) * 0.02)
    digits = []
    for contour in contours:
        x, y, w, h = cv2.boundingRect(contour)
        x = max(0, x - padding)
        y = max(0, y - padding)
        w = min(thresh.shape[1] - x, w + 2 * padding)
        h = min(thresh.shape[0] - y, h + 2 * padding)
        digit = thresh[y:y+h, x:x+w]
        digits.append(digit)
    
    return digits