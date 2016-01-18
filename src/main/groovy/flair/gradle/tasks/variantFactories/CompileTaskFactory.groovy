package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Compile
import flair.gradle.tasks.Group
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class CompileTaskFactory implements IVariantTaskFactory<Compile>
{
	public Compile create( Project project , Variant variant )
	{
		String name = "compile" + variant.name

		Compile t = project.tasks.findByName( name ) as Compile

		if( !t ) t = project.tasks.create( name , Compile )

		t.group = Group.BUILD.name
		t.variant = variant
		t.dependsOn project.tasks.getByName( Group.ASSEMBLE.name + variant.name ).name

		return t
	}
}