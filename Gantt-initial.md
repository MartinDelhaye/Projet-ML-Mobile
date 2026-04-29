```mermaid
gantt
    title Projet - Machine Learning Supervisé & Android
    dateFormat  YYYY-MM-DD

    section Autre
    Relâche                          :r1, 2026-02-28, 9d

    section Apprentissage
    Bases du Machine Learning         :a1, 2026-01-27, 25d
    Deep Learning                     :a2, after a1, 7d
    Prise en main de PyTorch          :a3, 2026-03-09, 6d

    section Développement
    Choix du cas d'étude              :b1, after a3, 3d
    Développement du modèle ML        :b2, after b1, 16d
    Développement Android             :b3, after b2, 14d
    Intégration du modèle             :b4, after b3, 6d
```
