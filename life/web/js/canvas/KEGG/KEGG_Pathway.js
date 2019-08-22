var KEGG = {"texts": ["HSA00531_GLYCOSAMINOGLYCAN_DEGRADATION", "HSA00604_GLYCOSPHINGOLIPID_BIOSYNTHESIS_GANGLIOSERIES", "HSA00602_GLYCOSPHINGOLIPID_BIOSYNTHESIS_NEO_LACTOSERIES", "HSA01031_GLYCAN_STRUCTURES_BIOSYNTHESIS_2", "HSA01030_GLYCAN_STRUCTURES_BIOSYNTHESIS_1", "HSA03020_RNA_POLYMERASE", "HSA00240_PYRIMIDINE_METABOLISM", "HSA03030_DNA_POLYMERASE", "HSA00940_PHENYLPROPANOID_BIOSYNTHESIS", "HSA00350_TYROSINE_METABOLISM", "HSA00950_ALKALOID_BIOSYNTHESIS_I", "HSA00031_INOSITOL_METABOLISM", "HSA00030_PENTOSE_PHOSPHATE_PATHWAY", "HSA00530_AMINOSUGARS_METABOLISM", "HSA01032_GLYCAN_STRUCTURES_DEGRADATION", "HSA00511_N_GLYCAN_DEGRADATION", "HSA00510_N_GLYCAN_BIOSYNTHESIS", "HSA00533_KERATAN_SULFATE_BIOSYNTHESIS", "HSA00601_GLYCOSPHINGOLIPID_BIOSYNTHESIS_LACTOSERIES", "HSA00780_BIOTIN_METABOLISM", "HSA04710_CIRCADIAN_RHYTHM", "HSA00642_ETHYLBENZENE_DEGRADATION", "HSA00626_NAPHTHALENE_AND_ANTHRACENE_DEGRADATION", "HSA00960_ALKALOID_BIOSYNTHESIS_II", "HSA00360_PHENYLALANINE_METABOLISM", "HSA00010_GLYCOLYSIS_AND_GLUCONEOGENESIS", "HSA00051_FRUCTOSE_AND_MANNOSE_METABOLISM", "HSA00521_STREPTOMYCIN_BIOSYNTHESIS", "HSA00232_CAFFEINE_METABOLISM", "HSA00561_GLYCEROLIPID_METABOLISM", "HSA00600_SPHINGOLIPID_METABOLISM", "HSA00562_INOSITOL_PHOSPHATE_METABOLISM", "HSA05110_CHOLERA_INFECTION", "HSA04540_GAP_JUNCTION", "HSA04340_HEDGEHOG_SIGNALING_PATHWAY", "HSA00361_GAMMA_HEXACHLOROCYCLOHEXANE_DEGRADATION", "HSA00632_BENZOATE_DEGRADATION_VIA_COA_LIGATION", "HSA00624_1_AND_2_METHYLNAPHTHALENE_DEGRADATION", "HSA00680_METHANE_METABOLISM", "HSA00120_BILE_ACID_BIOSYNTHESIS", "HSA00363_BISPHENOL_A_DEGRADATION", "HSA00052_GALACTOSE_METABOLISM", "HSA00565_ETHER_LIPID_METABOLISM", "HSA00564_GLYCEROPHOSPHOLIPID_METABOLISM", "HSA04070_PHOSPHATIDYLINOSITOL_SIGNALING_SYSTEM", "HSA04020_CALCIUM_SIGNALING_PATHWAY", "HSA04742_TASTE_TRANSDUCTION", "HSA04916_MELANOGENESIS", "HSA04740_OLFACTORY_TRANSDUCTION", "HSA00627_1,4_DICHLOROBENZENE_DEGRADATION", "HSA00903_LIMONENE_AND_PINENE_DEGRADATION", "HSA00071_FATTY_ACID_METABOLISM", "HSA01040_POLYUNSATURATED_FATTY_ACID_BIOSYNTHESIS", "HSA00650_BUTANOATE_METABOLISM", "HSA00625_TETRACHLOROETHENE_DEGRADATION", "HSA00591_LINOLEIC_ACID_METABOLISM", "HSA00592_ALPHA_LINOLENIC_ACID_METABOLISM", "HSA04730_LONG_TERM_DEPRESSION", "HSA00750_VITAMIN_B6_METABOLISM", "HSA04720_LONG_TERM_POTENTIATION", "HSA04912_GNRH_SIGNALING_PATHWAY", "HSA03010_RIBOSOME", "HSA04910_INSULIN_SIGNALING_PATHWAY", "HSA00053_ASCORBATE_AND_ALDARATE_METABOLISM", "HSA00410_BETA_ALANINE_METABOLISM", "HSA00280_VALINE_LEUCINE_AND_ISOLEUCINE_DEGRADATION", "HSA00062_FATTY_ACID_ELONGATION_IN_MITOCHONDRIA", "HSA00930_CAPROLACTAM_DEGRADATION", "HSA00310_LYSINE_DEGRADATION", "HSA00590_ARACHIDONIC_ACID_METABOLISM", "HSA04664_FC_EPSILON_RI_SIGNALING_PATHWAY", "HSA04370_VEGF_SIGNALING_PATHWAY", "HSA05211_RENAL_CELL_CARCINOMA", "HSA04320_DORSO_VENTRAL_AXIS_FORMATION", "HSA04010_MAPK_SIGNALING_PATHWAY", "HSA04012_ERBB_SIGNALING_PATHWAY", "HSA05214_GLIOMA", "HSA05040_HUNTINGTONS_DISEASE", "HSA00641_3_CHLOROACRYLIC_ACID_DEGRADATION", "HSA00532_CHONDROITIN_SULFATE_BIOSYNTHESIS", "HSA00640_PROPANOATE_METABOLISM", "HSA00072_SYNTHESIS_AND_DEGRADATION_OF_KETONE_BODIES", "HSA03320_PPAR_SIGNALING_PATHWAY", "HSA00563_GLYCOSYLPHOSPHATIDYLINOSITOL_ANCHOR_BIOSYNTHESIS", "HSA04150_MTOR_SIGNALING_PATHWAY", "HSA04670_LEUKOCYTE_TRANSENDOTHELIAL_MIGRATION", "HSA04660_T_CELL_RECEPTOR_SIGNALING_PATHWAY", "HSA04662_B_CELL_RECEPTOR_SIGNALING_PATHWAY", "HSA05218_MELANOMA", "HSA05216_THYROID_CANCER", "HSA05219_BLADDER_CANCER", "HSA04115_P53_SIGNALING_PATHWAY", "HSA04210_APOPTOSIS", "HSA00720_REDUCTIVE_CARBOXYLATE_CYCLE", "HSA00620_PYRUVATE_METABOLISM", "HSA00660_C5_BRANCHED_DIBASIC_ACID_METABOLISM", "HSA04950_MATURITY_ONSET_DIABETES_OF_THE_YOUNG", "HSA04930_TYPE_II_DIABETES_MELLITUS", "HSA04630_JAK_STAT_SIGNALING_PATHWAY", "HSA04920_ADIPOCYTOKINE_SIGNALING_PATHWAY", "HSA05120_EPITHELIAL_CELL_SIGNALING_IN_HELICOBACTER_PYLORI_INFECTION", "HSA05221_ACUTE_MYELOID_LEUKEMIA", "HSA05223_NON_SMALL_CELL_LUNG_CANCER", "HSA05212_PANCREATIC_CANCER", "HSA04110_CELL_CYCLE", "HSA05030_AMYOTROPHIC_LATERAL_SCLEROSIS", "HSA05050_DENTATORUBROPALLIDOLUYSIAN_ATROPHY", "HSA00630_GLYOXYLATE_AND_DICARBOXYLATE_METABOLISM", "HSA00020_CITRATE_CYCLE", "HSA00290_VALINE_LEUCINE_AND_ISOLEUCINE_BIOSYNTHESIS", "HSA02010_ABC_TRANSPORTERS_GENERAL", "HSA04140_REGULATION_OF_AUTOPHAGY", "HSA04060_CYTOKINE_CYTOKINE_RECEPTOR_INTERACTION", "HSA04620_TOLL_LIKE_RECEPTOR_SIGNALING_PATHWAY", "HSA00061_FATTY_ACID_BIOSYNTHESIS", "HSA05222_SMALL_CELL_LUNG_CANCER", "HSA05215_PROSTATE_CANCER", "HSA05220_CHRONIC_MYELOID_LEUKEMIA", "HSA04350_TGF_BETA_SIGNALING_PATHWAY", "HSA01510_NEURODEGENERATIVE_DISEASES", "HSA00300_LYSINE_BIOSYNTHESIS", "HSA00380_TRYPTOPHAN_METABOLISM", "HSA00260_GLYCINE_SERINE_AND_THREONINE_METABOLISM", "HSA00252_ALANINE_AND_ASPARTATE_METABOLISM", "HSA00472_D_ARGININE_AND_D_ORNITHINE_METABOLISM", "HSA04650_NATURAL_KILLER_CELL_MEDIATED_CYTOTOXICITY", "HSA04612_ANTIGEN_PROCESSING_AND_PRESENTATION", "HSA04514_CELL_ADHESION_MOLECULES", "HSA05060_PRION_DISEASE", "HSA04510_FOCAL_ADHESION", "HSA05213_ENDOMETRIAL_CANCER", "HSA05210_COLORECTAL_CANCER", "HSA04330_NOTCH_SIGNALING_PATHWAY", "HSA00534_HEPARAN_SULFATE_BIOSYNTHESIS", "HSA00440_AMINOPHOSPHONATE_METABOLISM", "HSA00140_C21_STEROID_HORMONE_METABOLISM", "HSA00220_UREA_CYCLE_AND_METABOLISM_OF_AMINO_GROUPS", "HSA00460_CYANOAMINO_ACID_METABOLISM", "HSA00430_TAURINE_AND_HYPOTAURINE_METABOLISM", "HSA04940_TYPE_I_DIABETES_MELLITUS", "HSA00900_TERPENOID_BIOSYNTHESIS", "HSA04640_HEMATOPOIETIC_CELL_LINEAGE", "HSA04512_ECM_RECEPTOR_INTERACTION", "HSA01430_CELL_COMMUNICATION", "HSA04520_ADHERENS_JUNCTION", "HSA04310_WNT_SIGNALING_PATHWAY", "HSA04350_TGF_BETA_SIGNALING_PATHWAY", "HSA05217_BASAL_CELL_CARCINOMA", "HSA00790_FOLATE_BIOSYNTHESIS", "HSA00340_HISTIDINE_METABOLISM", "HSA00910_NITROGEN_METABOLISM", "HSA00520_NUCLEOTIDE_SUGARS_METABOLISM", "HSA00480_GLUTATHIONE_METABOLISM", "HSA03060_PROTEIN_EXPORT", "HSA05010_ALZHEIMERS_DISEASE", "HSA04610_COMPLEMENT_AND_COAGULATION_CASCADES", "HSA00643_STYRENE_DEGRADATION", "HSA04810_REGULATION_OF_ACTIN_CYTOSKELETON", "HSA05131_PATHOGENIC_ESCHERICHIA_COLI_INFECTION_EPEC", "HSA04530_TIGHT_JUNCTION", "HSA00730_THIAMINE_METABOLISM", "HSA04120_UBIQUITIN_MEDIATED_PROTEOLYSIS", "HSA00550_PEPTIDOGLYCAN_BIOSYNTHESIS", "HSA00670_ONE_CARBON_POOL_BY_FOLATE", "HSA00471_D_GLUTAMINE_AND_D_GLUTAMATE_METABOLISM", "HSA00251_GLUTAMATE_METABOLISM", "HSA00902_MONOTERPENOID_BIOSYNTHESIS", "HSA00980_METABOLISM_OF_XENOBIOTICS_BY_CYTOCHROME_P450", "HSA05020_PARKINSONS_DISEASE", "HSA04614_RENIN_ANGIOTENSIN_SYSTEM", "HSA04080_NEUROACTIVE_LIGAND_RECEPTOR_INTERACTION", "HSA04360_AXON_GUIDANCE", "HSA05130_PATHOGENIC_ESCHERICHIA_COLI_INFECTION_EHEC", "HSA00770_PANTOTHENATE_AND_COA_BIOSYNTHESIS", "HSA00740_RIBOFLAVIN_METABOLISM", "HSA00920_SULFUR_METABOLISM", "HSA00271_METHIONINE_METABOLISM", "HSA00970_AMINOACYL_TRNA_BIOSYNTHESIS", "HSA00401_NOVOBIOCIN_BIOSYNTHESIS", "HSA00330_ARGININE_AND_PROLINE_METABOLISM", "HSA00860_PORPHYRIN_AND_CHLOROPHYLL_METABOLISM", "HSA00150_ANDROGEN_AND_ESTROGEN_METABOLISM", "HSA00100_BIOSYNTHESIS_OF_STEROIDS", "HSA00604_GLYCOSPHINGOLIPID_BIOSYNTHESIS_GANGLIOSERIES", "HSA00603_GLYCOSPHINGOLIPID_BIOSYNTHESIS_GLOBOSERIES", "HSA00130_UBIQUINONE_BIOSYNTHESIS", "HSA00512_O_GLYCAN_BIOSYNTHESIS", "HSA00190_OXIDATIVE_PHOSPHORYLATION", "HSA00230_PURINE_METABOLISM", "HSA00760_NICOTINATE_AND_NICOTINAMIDE_METABOLISM", "HSA00450_SELENOAMINO_ACID_METABOLISM", "HSA00272_CYSTEINE_METABOLISM", "HSA00400_PHENYLALANINE_TYROSINE_AND_TRYPTOPHAN_BIOSYNTHESIS", "HSA00710_CARBON_FIXATION", "HSA00040_PENTOSE_AND_GLUCURONATE_INTERCONVERSIONS", "HSA00500_STARCH_AND_SUCROSE_METABOLISM"], "weights": [6.621, 5.933000000000001, 6.849, 7.49, 3.882, 3.0, 5.0, 2.0, 6.6419999999999995, 7.7989999999999995, 5.922999999999999, 7.669, 7.771999999999999, 6.699, 6.34, 5.852, 4.971, 4.9879999999999995, 2.895, 2.975, 2.975, 2.997, 5.9959999999999996, 5.944, 6.471000000000001, 5.649, 7.944, 4.959, 5.728, 6.593999999999999, 4.844, 3.2880000000000003, 3.507, 6.33, 4.61, 4.978, 6.968, 7.885, 7.025, 7.325, 5.9799999999999995, 6.823, 5.995000000000001, 6.898000000000001, 6.574999999999999, 6.497, 7.5360000000000005, 7.037, 3.886, 2.956, 7.897, 7.773, 7.825, 7.719, 6.971, 7.942, 6.995, 7.058000000000001, 6.788, 6.539000000000001, 7.779000000000001, 7.624, 5.093, 3.9459999999999997, 4.942, 7.9030000000000005, 6.877, 5.795, 5.933000000000001, 6.898, 6.491, 7.584999999999999, 5.781, 4.86, 7.060999999999999, 7.219999999999999, 5.759, 4.366, 2.918, 6.903999999999999, 7.846, 3.732, 4.957, 4.643000000000001, 4.8740000000000006, 7.082, 7.135999999999999, 6.324, 6.962, 7.776999999999999, 7.168, 6.609, 3.9000000000000004, 5.0, 6.9239999999999995, 2.968, 1.935, 6.8740000000000006, 6.183, 5.997, 6.144, 6.989999999999999, 7.773, 7.957999999999999, 6.917000000000001, 6.140000000000001, 5.754999999999999, 3.962, 6.6259999999999994, 3.9829999999999997, 0.994, 5.5809999999999995, 6.595000000000001, 6.117000000000001, 6.3660000000000005, 7.098, 7.6770000000000005, 7.9, 4.95, 3.811, 2.971, 5.8709999999999996, 4.648, 6.864000000000001, 4.7170000000000005, 5.0, 6.981, 3.556, 6.493, 7.444, 6.581, 7.907, 6.181, 4.209, 3.0, 5.93, 4.9319999999999995, 5.872000000000001, 4.912, 3.933, 5.916, 5.756, 7.728, 4.475, 7.353, 7.176, 6.244, 1.657, 1.924, 4.911, 6.8709999999999996, 5.967, 4.997, 0.0, 4.798, 3.749, 5.805000000000001, 6.897, 5.904, 5.919, 4.971, 1.0, 4.868, 5.843999999999999, 3.9939999999999998, 6.964, 0.997, 3.997, 3.9779999999999998, 1.9769999999999999, 3.7800000000000002, 3.98, 3.931, 2.837, 4.613, 1.986, 4.9590000000000005, 5.853, 4.945, 5.659999999999999, 5.852, 5.978, 1.956, 3.939, 5.0, 4.686999999999999, 1.7069999999999999, 4.745, 7.7330000000000005, 2.982, 5.895, 7.4799999999999995, 7.813, 6.704999999999999, 5.777, 6.611]}