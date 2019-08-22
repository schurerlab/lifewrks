var assayData = [
    {AssayType:'KINOMEscan assay', 
        perturbagenType:{SmallMolecule:136, shRNA:0, cDNA:0},
        perturbationType:{		
            'percent G2-M arrested':0,
            'percent Interphase':0,
            'percent Mitosis':0,
            'percent Non Arrested':0,
            'IC50':0,
            'percent Apoptosis':0,
            'percent Cell Viability':0,
            'percent Kinase Inhibition':40055,
            'fold Change':0,
            'z-score':0,
            'cell viability z-score':0,
            'pKd':6968
         },
        measuredEntity:{Cells:0,KinaseProteins:475,Antibodies:0,TranscribedGenes:0,PhosphoProteins:0},
        modelSystems:{} 
    },
    {AssayType:'Kinative assay', 
        perturbagenType:{SmallMolecule:17, shRNA:0, cDNA:0}, 
        perturbationType:{
                   'percent G2-M arrested':0,
                   'percent Interphase':0,
                   'percent Mitosis':0,
                   'percent Non Arrested':0,
                   'IC50':89,
                   'percent Apoptosis':0,
                   'percent Cell Viability':0,
		           'percent Kinase Inhibition':2040,
                   'fold Change':0,
                   'z-score':0,
                   'cell viability z-score':0,
                   'pKd':0},
        measuredEntity:{Cells:3,KinaseProteins:169,Antibodies:0,TranscribedGenes:0,PhosphoProteins:0},
        modelSystems:{Cells:3}
    },
    {AssayType:'L1000 transcriptional profiling assay', 
        perturbagenType:{SmallMolecule:8999, shRNA:17253, cDNA:799}, 
        perturbationType:{
        'percent G2-M arrested':0,
        'percent Interphase':0,
        'percent Mitosis':0,
        'percent Non Arrested':0,
        'IC50':0,
        'percent Apoptosis':0,
        'percent Cell Viability':0,
        'percent Kinase Inhibition':0,
        'fold Change':0,
        'z-score':11341187,
        'cell viability z-score':0,
        'pKd':0},
        measuredEntity:{Cells:41,KinaseProteins:0,Antibodies:0,TranscribedGenes:978,PhosphoProteins:0},
        modelSystems:{Cells:41}
    },
    {AssayType:'Apoptosis assay', 
        perturbagenType:{SmallMolecule:8, shRNA:0, cDNA:0}, 
        perturbationType:{'percent G2-M arrested':0,
        'percent Interphase':0,
        'percent Mitosis':0,
        'percent Non Arrested':0,
        'IC50':0,
        'percent Apoptosis':4800,
        'percent Cell Viability':0,
        'percent Kinase Inhibition':0,
        'fold Change':0,
        'z-score':0,
        'cell viability z-score':0,
        'pKd':0
        },
        measuredEntity:{Cells:11,KinaseProteins:0,Antibodies:0,TranscribedGenes:0,PhosphoProteins:0},
        modelSystems:{}        
    },
    {AssayType:'Cell cycle state assay', 
        perturbagenType:{SmallMolecule:12, shRNA:0, cDNA:0}, 
        perturbationType:{
        'percent G2-M arrested':9216,
        'percent Interphase':20905,
        'percent Mitosis':20905,
        'percent Non Arrested':20905,
        'IC50':0,
        'percent Apoptosis':0,
        'percent Cell Viability':0,
        'percent Kinase Inhibition':0,
        'fold Change':0,
        'z-score':0,
        'cell viability z-score':0,
        'pKd':0},
        measuredEntity:{Cells:36,KinaseProteins:0,Antibodies:0,TranscribedGenes:0,PhosphoProteins:0},
        modelSystems:{}
    },
    {AssayType:'Cell viability assay',  
        perturbagenType:{SmallMolecule:54, shRNA:0, cDNA:0}, 
        perturbationType:{'percent G2-M arrested':0,
        'percent Interphase':0,
        'percent Mitosis':0,
        'percent Non Arrested':0,
        'IC50':0,
        'percent Apoptosis':0,
        'percent Cell Viability':4450,
        'percent Kinase Inhibition':0,
        'fold Change':0,
        'z-score':0,
        'cell viability z-score':0,
        'pKd':0},
        measuredEntity:{Cells:50,KinaseProteins:0,Antibodies:0,TranscribedGenes:0,PhosphoProteins:0},
        modelSystems:{}
    },
    {AssayType:'P100 phospho protein profiling assay',  
        perturbagenType:{SmallMolecule:15, shRNA:0, cDNA:0}, 
        perturbationType:{'percent G2-M arrested':0,
        'percent Interphase':0,
        'percent Mitosis':0,
        'percent Non Arrested':0,
        'IC50':0,
        'percent Apoptosis':0,
        'percent Cell Viability':0,
        'percent Kinase Inhibition':0,
        'fold Change':11880,
        'z-score':0,
        'cell viability z-score':0,
        'pKd':0},
        measuredEntity:{Cells:3,KinaseProteins:0,Antibodies:0,TranscribedGenes:0,PhosphoProteins:81},
        modelSystems:{}
    },
    {AssayType:'Single cell protein secretion profiling assay',  
        perturbagenType:{SmallMolecule:5, shRNA:0, cDNA:0}, 
        perturbationType:{'percent G2-M arrested':0,
        'percent Interphase':0,
        'percent Mitosis':0,
        'percent Non Arrested':0,
        'IC50':0,
        'percent Apoptosis':0,
        'percent Cell Viability':0,
        'percent Kinase Inhibition':0,
        'fold Change':0,
        'z-score':0,
        'cell viability z-score':13664,
        'pKd':0},
        measuredEntity:{Cells:5,KinaseProteins:0,Antibodies:35,TranscribedGenes:0,PhosphoProteins:0},
        modelSystems:{}
    },
    {AssayType:'Cell growth inhibition assay',  
        perturbagenType:{SmallMolecule:44, shRNA:0, cDNA:0}, 
        perturbationType:{'percent G2-M arrested':0,
        'percent Interphase':0,
        'percent Mitosis':0,
        'percent Non Arrested':0,
        'IC50':0,
        'percent Apoptosis':0,
        'percent Cell Viability':91716,
        'percent Kinase Inhibition':0,
        'fold Change':0,
        'z-score':0,
        'cell viability z-score':0,
        'pKd':0},
        measuredEntity:{Cells:1058,KinaseProteins:0,Antibodies:0,TranscribedGenes:0,PhosphoProteins:0},
        modelSystems:{}
    }
];


var totalData = [{
    perturbagenType:{SmallMoleculeTotalCount:9072,
                     shRNATotalCount:17253,
                     cDNATotalCount:799},
    measuredEntityType:{"CellsTotalCount":1078,"KinaseProteinsTotalCount":475,
                         "AntibodiesTotalCount":80,"TranscribedGenesTotalCount":978,
                         "ProteinsTotalCount":5886,"PhosphoProteinsTotalCount":81},
    modelSystemType:{cells:92},
    perturbationType:{
    	"percent G2-M arrested Total SmallMolecule":8,
        "percent Interphase Total SmallMolecule":12,
        "percent Mitosis Total SmallMolecule":12,
        "percent Non Arrested Total SmallMolecule":12,
        "IC50 Total SmallMolecule":6,
        "percent Kinase Inhibition Total SmallMolecule":106,
        "percent Apoptosis Total SmallMolecule":7,
        "percent Cell Viability Total SmallMolecule":44,
        "fold Change Total SmallMolecule":15,
        "z-score Total SmallMolecule":8999,
        "cell viability z-score Total SmallMolecule":54,
        "pKd Total SmallMolecule":56,
        "percent G2-M arrested Total shRNA":0,
        "percent Interphase Total shRNA":0,
        "percent Mitosis Total shRNA":0,
        "percent Non Arrested Total shRNA":0,
        "IC50 Total shRNA":0,
        "percent Kinase Inhibition Total shRNA":0,
        "percent Apoptosis Total shRNA":0,
        "percent Cell Viability Total shRNA":0,
        "fold Change Total shRNA":0,
        "z-score Total shRNA":17253,
        "cell viability z-score Total shRNA":0,
        "pKd Total shRNA":0,
        "percent G2-M arrested Total cDNA":0,
        "percent Interphase Total cDNA":0,
        "percent Mitosis Total cDNA":0,
        "percent Non Arrested Total cDNA":0,
        "IC50 Total cDNA":0,
        "percent Kinase Inhibition Total cDNA":0,
        "percent Apoptosis Total cDNA":0,
        "percent Cell Viability Total cDNA":0,
        "fold Change Total cDNA":0,
        "z-score Total cDNA":799,
        "cell viability z-score Total cDNA":0,
        "pKd Total cDNA":0,
        "SmallMolecule Total percent G2-M arrested":9216,
        "SmallMolecule Total percent Interphase":20905,
        "SmallMolecule Total percent Mitosis":20905,
        "SmallMolecule Total percent Non Arrested":20905,
        "SmallMolecule Total IC50":89,
        "SmallMolecule Total percent Kinase Inhibition":42095,
        "SmallMolecule Total percent Apoptosis":4800,
        "SmallMolecule Total percent Cell Viability":96166,
        "SmallMolecule Total fold Change":11880,
        "SmallMolecule Total z-score":2746913,
        "SmallMolecule Total cell viability z-score":13664,
        "SmallMolecule Total pKd":6968,
        "shRNA Total percent G2-M arrested":0,
        "shRNA Total percent Interphase":0,
        "shRNA Total percent Mitosis":0,
        "shRNA Total percent Non Arrested":0,
        "shRNA Total IC50":0,
        "shRNA Total percent Kinase Inhibition":0,
        "shRNA Total percent Apoptosis":0,
        "shRNA Total percent Cell Viability":0,
        "shRNA Total fold Change":0,
        "shRNA Total z-score":8253074,
        "shRNA Total cell viability z-score":0,
        "shRNA Total pKd":0,
        "cDNA Total percent G2-M arrested":0,
        "cDNA Total percent Interphase":0,
        "cDNA Total percent Mitosis":0,
        "cDNA Total percent Non Arrested":0,
        "cDNA Total IC50":0,
        "cDNA Total percent Kinase Inhibition":0,
        "cDNA Total percent Apoptosis":0,
        "cDNA Total percent Cell Viability":0,
        "cDNA Total fold Change":0,
        "cDNA Total z-score":341200,
        "cDNA Total cell viability z-score":0,
        "cDNA Total pKd":0,
        
        "SmallMolecule Total Cells":1078,
        "SmallMolecule Total KinaseProteins":475,
        "SmallMolecule Total Antibodies":79,
        "SmallMolecule Total TranscribedGenes":978,
        "SmallMolecule Total Proteins":0,
        "SmallMolecule Total PhosphoProteins":81,
        "shRNA Total Cells":41,
        "shRNA Total KinaseProteins":0,
        "shRNA Total Antibodies":0,
        "shRNA Total TranscribedGenes":978,
        "shRNA Total Proteins":0,
        "shRNA Total PhosphoProteins":0,
        "cDNA Total Cells":41,
        "cDNA Total KinaseProteins":0,
        "cDNA Total Antibodies":0,
        "cDNA Total TranscribedGenes":978,
        "cDNA Total Proteins":0,
        "cDNA Total PhosphoProteins":0,      
        
        "Cells Total SmallMolecule":9056,
        "KinaseProteins Total SmallMolecule":137,
        "Antibodies Total SmallMolecule":24,
        "TranscribedGenes Total SmallMolecule":8999,
        "Proteins Total SmallMolecule":21,
        "PhosphoProteins Total SmallMolecule":15,
        
        "Cells Total shRNA":17253,
        "KinaseProteins Total shRNA":0,
        "Antibodies Total shRNA":0,
        "TranscribedGenes Total shRNA":17253,
        "Proteins Total shRNA":0,
        "PhosphoProteins Total shRNA":0,
        
        "Cells Total cDNA":799,
        "KinaseProteins Total cDNA":0,
        "Antibodies Total cDNA":0,
        "TranscribedGenes Total cDNA":799,
        "Proteins Total cDNA":0,
        "PhosphoProteins Total cDNA":0,
        
        "percent G2-M arrested Total Cells":36,
        "percent Interphase Total Cells":36,
        "percent Mitosis Total Cells":36,
        "percent Non Arrested Total Cells":36,
        "IC50 Total Cells":1,
        "percent Kinase Inhibition Total Cells":3,
        "percent Apoptosis Total Cells":11,
        "percent Cell Viability Total Cells":1058,
        "fold Change Total Cells":3,
        "z-score Total Cells":41,
        "cell viability z-score Total Cells":50,
        "pKd Total Cells":0,
        "Cells Total percent G2-M arrested":9216,
		"Cells Total percent Interphase":20905,
		"Cells Total percent Mitosis":20905,
		"Cells Total percent Non Arrested":20905,
		"Cells Total IC50":89,
		"Cells Total percent Kinase Inhibition":2040,
		"Cells Total percent Apoptosis":4800,
		"Cells Total percent Cell Viability":96166,
		"Cells Total fold Change":11880,
		"Cells Total z-score":11341187,
		"Cells Total cell viability z-score":50,
		"Cells Total pKd":0,	
		"percent G2-M arrested Total TranscribedGenes":0,
        "percent Interphase Total TranscribedGenes":0,
        "percent Mitosis Total TranscribedGenes":0,
        "percent Non Arrested Total TranscribedGenes":0,
        "IC50 Total TranscribedGenes":0,
        "percent Kinase Inhibition Total TranscribedGenes":0,
        "percent Apoptosis Total TranscribedGenes":0,
        "percent Cell Viability Total TranscribedGenes":0,
        "fold Change Total TranscribedGenes":0,
        "z-score Total TranscribedGenes":978,
        "cell viability z-score Total TranscribedGenes":0,
        "pKd Total TranscribedGenes":0,
        "TranscribedGenes Total percent G2-M arrested":0,
		"TranscribedGenes Total percent Interphase":0,
		"TranscribedGenes Total percent Mitosis":0,
		"TranscribedGenes Total percent Non Arrested":0,
		"TranscribedGenes Total IC50":0,
		"TranscribedGenes Total percent Kinase Inhibition":0,
		"TranscribedGenes Total percent Apoptosis":0,
		"TranscribedGenes Total percent Cell Viability":0,
		"TranscribedGenes Total fold Change":0,
		"TranscribedGenes Total z-score":11341187,
		"TranscribedGenes Total cell viability z-score":0,
		"TranscribedGenes Total pKd":0,
    	"percent G2-M arrested Total Antibodies":0,
        "percent Interphase Total Antibodies":0,
        "percent Mitosis Total Antibodies":0,
        "percent Non Arrested Total Antibodies":0,
        "IC50 Total Antibodies":0,
        "percent Kinase Inhibition Total Antibodies":0,
        "percent Apoptosis Total Antibodies":0,
        "percent Cell Viability Total Antibodies":0,
        "fold Change Total Antibodies":0,
        "z-score Total Antibodies":0,
        "cell viability z-score Total Antibodies":0,
        "pKd Total Antibodies":0,
    	"Antibodies Total percent G2-M arrested":0,
        "Antibodies Total percent Interphase":0,
        "Antibodies Total percent Mitosis":0,
        "Antibodies Total percent Non Arrested":0,
        "Antibodies Total IC50":0,
        "Antibodies Total percent Kinase Inhibition":0,
        "Antibodies Total percent Apoptosis":0,
        "Antibodies Total percent Cell Viability":0,
        "Antibodies Total fold Change":0,
        "Antibodies Total z-score":0,
        "Antibodies Total cell viability z-score":0,
        "Antibodies Total pKd":0,
    	"percent G2-M arrested Total KinaseProteins":0,
        "percent Interphase Total KinaseProteins":0,
        "percent Mitosis Total KinaseProteins":0,
        "percent Non Arrested Total KinaseProteins":0,
        "IC50 Total KinaseProteins":34,
        "percent Kinase Inhibition Total KinaseProteins":475,
        "percent Apoptosis Total KinaseProteins":0,
        "percent Cell Viability Total KinaseProteins":0,
        "fold Change Total KinaseProteins":0,
        "z-score Total KinaseProteins":0,
        "cell viability z-score Total KinaseProteins":0,
        "pKd Total KinaseProteins":441,
        "KinaseProteins Total percent G2-M arrested":0,
        "KinaseProteins Total percent Interphase":0,
        "KinaseProteins Total percent Mitosis":0,
        "KinaseProteins Total percent Non Arrested":0,
        "KinaseProteins Total IC50":89,
        "KinaseProteins Total percent Kinase Inhibition":42095,
        "KinaseProteins Total percent Apoptosis":0,
        "KinaseProteins Total percent Cell Viability":0,
        "KinaseProteins Total fold Change":0,
        "KinaseProteins Total z-score":0,
        "KinaseProteins Total cell viability z-score":0,
        "KinaseProteins Total pKd":6968,
    	"percent G2-M arrested Total Proteins":0,
        "percent Interphase Total Proteins":0,
        "percent Mitosis Total Proteins":0,
        "percent Non Arrested Total Proteins":0,
        "IC50 Total Proteins":0,
        "percent Kinase Inhibition Total Proteins":0,
        "percent Apoptosis Total Proteins":0,
        "percent Cell Viability Total Proteins":0,
        "fold Change Total Proteins":0,
        "z-score Total Proteins":0,
        "cell viability z-score Total Proteins":0,
        "pKd Total Proteins":0,
        "Proteins Total percent G2-M arrested":0,
        "Proteins Total percent Interphase":0,
        "Proteins Total percent Mitosis":0,
        "Proteins Total percent Non Arrested":0,
        "Proteins Total IC50":0,
        "Proteins Total percent Kinase Inhibition":0,
        "Proteins Total percent Apoptosis":0,
        "Proteins Total percent Cell Viability":0,
        "Proteins Total fold Change":0,
        "Proteins Total z-score":0,
        "Proteins Total cell viability z-score":0,
        "Proteins Total pKd":0,
        "percent G2-M arrested Total PhosphoProteins":0,
        "percent Interphase Total PhosphoProteins":0,
        "percent Mitosis Total PhosphoProteins":0,
        "percent Non Arrested Total PhosphoProteins":0,
        "IC50 Total PhosphoProteins":0,
        "percent Kinase Inhibition Total PhosphoProteins":0,
        "percent Apoptosis Total PhosphoProteins":0,
        "percent Cell Viability Total PhosphoProteins":0,
        "fold Change Total PhosphoProteins":81,
        "z-score Total PhosphoProteins":0,
        "cell viability z-score Total PhosphoProteins":0,
        "pKd Total PhosphoProteins":0,
        "PhosphoProteins Total percent G2-M arrested":0,
        "PhosphoProteins Total percent Interphase":0,
        "PhosphoProteins Total percent Mitosis":0,
        "PhosphoProteins Total percent Non Arrested":0,
        "PhosphoProteins Total IC50":0,
        "PhosphoProteins Total percent Kinase Inhibition":0,
        "PhosphoProteins Total percent Apoptosis":0,
        "PhosphoProteins Total percent Cell Viability":0,
        "PhosphoProteins Total fold Change":11880,
        "PhosphoProteins Total z-score":0,
        "PhosphoProteins Total cell viability z-score":0,
        "PhosphoProteins Total pKd":0}
}];